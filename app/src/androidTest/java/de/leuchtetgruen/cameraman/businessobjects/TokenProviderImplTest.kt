package de.leuchtetgruen.cameraman.businessobjects

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class TokenProviderImplTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    var oldRefreshToken : String? = null
    lateinit var tokenProviderImpl: TokenProviderImpl

    @Before
    fun setUp() {
        tokenProviderImpl = TokenProviderImpl(composeRule.activity.applicationContext)
        if (tokenProviderImpl.hasRefreshToken()) {
            oldRefreshToken = tokenProviderImpl.getRefreshToken()
        }
    }

    @Test
    fun empty_refresh_token_will_lead_to_has_refresh_token_false() {
        composeRule.activity.applicationContext.getSharedPreferences(tokenProviderImpl.SHARED_PREFS_KEY, 0).edit().clear().apply()

        assertFalse(tokenProviderImpl.hasRefreshToken())
    }

    @Test
    fun setting_refresh_token_will_lead_to_has_refresh_token_true() {
        tokenProviderImpl.setRefreshToken("foobar")

        assert(tokenProviderImpl.hasRefreshToken())
    }

    @Test
    fun setting_refresh_token_will_lead_to_no_need_for_login() {
        tokenProviderImpl.setRefreshToken("foobar")

        assertFalse(tokenProviderImpl.needsLogin())
    }

    @Test
    fun set_refresh_token_can_be_read() {
        val tokenValue = "jkerwljr"
        tokenProviderImpl.setRefreshToken(tokenValue)

        assertEquals(tokenValue, tokenProviderImpl.getRefreshToken())
    }

    @After
    fun tearDown() {
        if (oldRefreshToken == null) return
        tokenProviderImpl.setRefreshToken(oldRefreshToken!!)
    }
}