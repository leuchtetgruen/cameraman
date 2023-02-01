package de.leuchtetgruen.cameraman.api

import org.junit.Assert.assertFalse
import org.junit.Test

class RuntimeTokenStoreTest {

    @Test
    fun `has token works correctly if no token is set`(){
        val rts = RuntimeTokenStore(null, "foo")
        assertFalse(rts.hasToken())
    }

    @Test
    fun `has token works correctly if  token is set`(){
        val rts = RuntimeTokenStore("foo", "foo")
        assert(rts.hasToken())
    }

    @Test
    fun `has refresh token works correctly if no refresh token is set`(){
        val rts = RuntimeTokenStore("foo", null)
        assertFalse(rts.hasRefreshToken())
    }

    @Test
    fun `has refresh token works correctly if  refresh token is set`(){
        val rts = RuntimeTokenStore("foo", "foo")
        assert(rts.hasRefreshToken())
    }
}