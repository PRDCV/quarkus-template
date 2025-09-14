package vvu.centrauthz.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import vvu.centrauthz.errors.BadRequestError;
import vvu.centrauthz.errors.PageTokenEncodingError;

public class PageToken {

    private PageToken() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Encodes pagination parameters into a Base64 page token.
     *
     * @param offset The offset value
     * @return Base64 encoded page token
     * @throws RuntimeException if failed to encode page token
     */
    public static String encodePageToken(Integer offset) {
        try {
            var objectMapper = JsonTools.mapper();
            ObjectNode tokenData = objectMapper.createObjectNode();
            tokenData.put("offset", offset);

            String jsonString = objectMapper.writeValueAsString(tokenData);
            return Base64.getEncoder().encodeToString(jsonString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new PageTokenEncodingError("Failed to encode page token");
        }
    }

    /**
     * Decodes a Base64 page token back to JsonNode.
     *
     * @param pageToken Base64 encoded page token
     * @return JsonNode containing the decoded data
     * @throws BadRequestError if the page token is invalid or malformed
     */
    public static JsonNode decodePageToken(String pageToken) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(pageToken);
            String jsonString = new String(decodedBytes, StandardCharsets.UTF_8);
            return JsonTools.mapper().readTree(jsonString);
        } catch (Exception e) {
            throw new BadRequestError("Failed to decode page token");
        }
    }

    /**
     * Extracts offset from a page token.
     *
     * @param pageToken Base64 encoded page token
     * @return The offset value
     */
    public static Integer getOffset(String pageToken) {
        JsonNode tokenData = decodePageToken(pageToken);
        JsonNode offsetNode = tokenData.get("offset");
        return offsetNode != null ? offsetNode.asInt(0) : 0;
    }

    /**
     * Validates if a page token is valid by attempting to decode it.
     * Does nothing if the page token is null.
     * Throws an exception if the token is invalid or cannot be decoded.
     *
     * @param pageToken Base64 encoded page token, may be null
     * @throws BadRequestError if the page token is invalid or malformed
     */
    public static void validatePageToken(String pageToken) {
        if (pageToken != null) {
            decodePageToken(pageToken);
        }
    }
}
