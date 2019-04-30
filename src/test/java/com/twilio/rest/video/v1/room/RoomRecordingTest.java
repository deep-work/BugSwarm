/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.video.v1.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.converter.DateConverter;
import com.twilio.converter.Promoter;
import com.twilio.exception.TwilioException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static com.twilio.TwilioTest.serialize;
import static org.junit.Assert.*;

public class RoomRecordingTest {
    @Mocked
    private TwilioRestClient twilioRestClient;

    @Before
    public void setUp() throws Exception {
        Twilio.init("AC123", "AUTH TOKEN");
    }

    @Test
    public void testFetchRequest() {
        new NonStrictExpectations() {{
            Request request = new Request(HttpMethod.GET,
                                          Domains.VIDEO.toString(),
                                          "/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings/RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            
            twilioRestClient.request(request);
            times = 1;
            result = new Response("", 500);
            twilioRestClient.getAccountSid();
            result = "AC123";
        }};

        try {
            RoomRecording.fetcher("RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").fetch();
            fail("Expected TwilioException to be thrown for 500");
        } catch (TwilioException e) {}
    }

    @Test
    public void testFetchResponse() {
        new NonStrictExpectations() {{
            twilioRestClient.request((Request) any);
            result = new Response("{\"account_sid\": \"ACaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"status\": \"processing\",\"date_created\": \"2015-07-30T20:00:00Z\",\"sid\": \"RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"source_sid\": \"MTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"size\": 0,\"type\": \"audio\",\"duration\": 0,\"container_format\": \"mka\",\"codec\": \"OPUS\",\"grouping_sids\": {\"room_sid\": \"RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"},\"room_sid\": \"RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings/RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"links\": {\"media\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings/RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Media\"}}", TwilioRestClient.HTTP_STATUS_CODE_OK);
            twilioRestClient.getObjectMapper();
            result = new ObjectMapper();
        }};

        assertNotNull(RoomRecording.fetcher("RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").fetch());
    }

    @Test
    public void testReadRequest() {
        new NonStrictExpectations() {{
            Request request = new Request(HttpMethod.GET,
                                          Domains.VIDEO.toString(),
                                          "/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings");
            
            twilioRestClient.request(request);
            times = 1;
            result = new Response("", 500);
            twilioRestClient.getAccountSid();
            result = "AC123";
        }};

        try {
            RoomRecording.reader("RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").read();
            fail("Expected TwilioException to be thrown for 500");
        } catch (TwilioException e) {}
    }

    @Test
    public void testReadEmptyResponse() {
        new NonStrictExpectations() {{
            twilioRestClient.request((Request) any);
            result = new Response("{\"recordings\": [],\"meta\": {\"page\": 0,\"page_size\": 50,\"first_page_url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings?PageSize=50&Page=0\",\"previous_page_url\": null,\"url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings?PageSize=50&Page=0\",\"next_page_url\": null,\"key\": \"recordings\"}}", TwilioRestClient.HTTP_STATUS_CODE_OK);
            twilioRestClient.getObjectMapper();
            result = new ObjectMapper();
        }};

        assertNotNull(RoomRecording.reader("RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").read());
    }

    @Test
    public void testReadResultsResponse() {
        new NonStrictExpectations() {{
            twilioRestClient.request((Request) any);
            result = new Response("{\"recordings\": [{\"account_sid\": \"ACaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"status\": \"completed\",\"date_created\": \"2015-07-30T20:00:00Z\",\"sid\": \"RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"source_sid\": \"MTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"size\": 0,\"type\": \"audio\",\"duration\": 0,\"container_format\": \"mka\",\"codec\": \"OPUS\",\"grouping_sids\": {\"room_sid\": \"RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"},\"room_sid\": \"RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings/RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"links\": {\"media\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings/RTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Media\"}}],\"meta\": {\"page\": 0,\"page_size\": 50,\"first_page_url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings?PageSize=50&Page=0\",\"previous_page_url\": null,\"url\": \"https://video.twilio.com/v1/Rooms/RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/Recordings?PageSize=50&Page=0\",\"next_page_url\": null,\"key\": \"recordings\"}}", TwilioRestClient.HTTP_STATUS_CODE_OK);
            twilioRestClient.getObjectMapper();
            result = new ObjectMapper();
        }};

        assertNotNull(RoomRecording.reader("RMaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").read());
    }
}