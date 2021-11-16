package com.easygoing.backend.services.user.service

import com.easygoing.backend.services.user.constant.RoleType
import com.easygoing.backend.services.user.converter.UserConverter
import com.easygoing.backend.services.user.dao.AuthorityDao
import com.easygoing.backend.services.user.dao.UserDao
import com.easygoing.backend.services.user.dto.OAuth2UserInfo
import com.easygoing.backend.services.user.factory.OAuth2UserInfoFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserServiceImpl: DefaultOAuth2UserService() {

    @Autowired
    private lateinit var userConverter: UserConverter

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        return try {
            if(userRequest == null){
                throw Exception("OAuth2UserRequest is null")
            }
            processOAuth2User(userRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.clientRegistration.registrationId,
            oAuth2User.attributes
        )
        if (oAuth2UserInfo.email.isNullOrBlank()) {
            throw InternalAuthenticationServiceException("Email not found from OAuth2 provider")
        }else{
            val user = customUserDetailsService.findUserByEmail(oAuth2UserInfo.email.toString())
            val curUser = if( user != null ){
                if ( !user.authProvider.toString().equals(oAuth2UserRequest.clientRegistration.registrationId, ignoreCase = true )){
                    throw InternalAuthenticationServiceException("You have signed up with " + user.authProvider + " account with same email. Please use your " + user.authProvider + " account to login.")
                }
                updateExistingUser(user, oAuth2UserInfo)
            }else{
                registerNewUser(oAuth2UserInfo)
            }
            return userConverter.userDaoToCustomUserDetail(curUser)
        }

    }

    private fun updateExistingUser(existingUser: UserDao, oAuth2UserInfo: OAuth2UserInfo): UserDao {
        if( customUserDetailsService.isValidUserName(oAuth2UserInfo.name.toString()) ){
            existingUser.username = oAuth2UserInfo.name.toString()
        }else{
            existingUser.username = oAuth2UserInfo.name.toString() + customUserDetailsService.suggestId().toString()
        }
        return customUserDetailsService.updateUser(existingUser)
    }

    private fun registerNewUser(oAuth2UserInfo: OAuth2UserInfo): UserDao {
        val authorities = listOf(AuthorityDao(authority = RoleType.USER.role))
        val newUser = userConverter.oAuth2UserInfoToUserDao(oAuth2UserInfo, authorities)
        if( customUserDetailsService.isValidUserName(newUser.username) ){
            newUser.username = newUser.username
        }else{
            newUser.username = newUser.username + customUserDetailsService.suggestId().toString()
        }
        return customUserDetailsService.createUser(newUser)
    }

}