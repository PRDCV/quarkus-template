package vvu.centrauthz.utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import vvu.centrauthz.errors.BadRequestError;

class PageTokenTest {

    @Test
    void testEncodePageToken() {
        String token = PageToken.encodePageToken(10);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testDecodePageToken() {
        String token = PageToken.encodePageToken(5);
        var decoded = PageToken.decodePageToken(token);
        assertNotNull(decoded);
        assertEquals(5, decoded.get("offset").asInt());
    }

    @Test
    void testDecodePageToken_InvalidToken() {
        assertThrows(BadRequestError.class, () -> PageToken.decodePageToken("invalid"));
    }

    @Test
    void testGetOffset() {
        String token = PageToken.encodePageToken(15);
        Integer offset = PageToken.getOffset(token);
        assertEquals(15, offset);
    }

    @Test
    void testGetOffset_DefaultValue() {
        // Create token without offset field to test default
        String invalidToken = "e30="; // Base64 for "{}"
        Integer offset = PageToken.getOffset(invalidToken);
        assertEquals(0, offset);
    }

    @Test
    void testValidatePageToken_Valid() {
        String token = PageToken.encodePageToken(20);
        assertDoesNotThrow(() -> PageToken.validatePageToken(token));
    }

    @Test
    void testValidatePageToken_Null() {
        assertDoesNotThrow(() -> PageToken.validatePageToken(null));
    }

    @Test
    void testValidatePageToken_Invalid() {
        assertThrows(BadRequestError.class, () -> PageToken.validatePageToken("invalid"));
    }
}
