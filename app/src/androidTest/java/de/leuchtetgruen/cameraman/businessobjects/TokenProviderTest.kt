package de.leuchtetgruen.cameraman.businessobjects

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class TokenProviderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        val ctx = composeRule.activity.applicationContext
        ctx.getSharedPreferences(TokenProvider.SHARED_PREFS_KEY, 0).edit().clear().apply()
    }

    @Test
    fun can_read_written_refresh_token() {
        val token = "foobar"
        TokenProvider.setRefreshToken(composeRule.activity.applicationContext, token)
        val readToken = TokenProvider.getRefreshToken(composeRule.activity.applicationContext)
        Assert.assertEquals(readToken, token)
    }

    @Test
    fun checking_refresh_token_exists_works() {
        TokenProvider.setRefreshToken(composeRule.activity.applicationContext, "anytoken")
        Assert.assertTrue(TokenProvider.hasRefreshToken(composeRule.activity.applicationContext))
    }

    @Test
    fun check_needs_login_at_beginning() {
        Assert.assertTrue(TokenProvider.needsLogin(composeRule.activity.applicationContext))
    }

}