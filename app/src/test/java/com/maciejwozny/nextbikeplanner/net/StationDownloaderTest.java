package com.maciejwozny.nextbikeplanner.net;

import com.maciejwozny.nextbikeplanner.station.DataDownloader;
import com.maciejwozny.nextbikeplanner.station.StationDownloader;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class StationDownloaderTest {
    private DataDownloader dataDownloader = mock(DataDownloader.class);
    private StationDownloader sut;

    @Before
    public void setUp() {
//        sut = new StationDownloader(dataDownloader);
//        when(dataDownloader.downloadFile()).thenReturn(JSON);
    }

//    @Test
//    public void shouldCorrectParseJsonToStationList() {
//        List<Station> stationList = sut.createStationList();
//
//        assertEquals(3, stationList.size());
//
//        assertEquals("Pl. Kromera", stationList.get(0).getName());
//        assertEquals("Dworzec Nadodrze", stationList.get(1).getName());
//        assertEquals("Politechnika Wroc\u0142awska - Gmach G\u0142\u00f3wny",
//                stationList.get(2).getName());
//
//        assertEquals(5, stationList.get(0).getBikeNumber());
//        assertEquals(10, stationList.get(0).getFreeRacksNumber());
//        assertEquals(51.132077147496, stationList.get(0).getLatitude(), 0.0000000000001);
//        assertEquals(17.065501213074, stationList.get(0).getLongitude(), 0.0000000000001);
//        assertEquals(5901, stationList.get(0).getIdNumber());
//    }


    private static final String JSON = "{" +
            "  \"countries\": [" +
            "    {" +
            "      \"lat\": 52.2413," +
            "      \"lng\": 18.479," +
            "      \"zoom\": 6," +
            "      \"name\": \"WRM nextbike Poland\"," +
            "      \"hotline\": \"0048717381111\"," +
            "      \"domain\": \"pl\"," +
            "      \"language\": \"pl\"," +
            "      \"email\": \"bok@wroclawskirower.pl\"," +
            "      \"timezone\": \"Europe/Warsaw\"," +
            "      \"currency\": \"PLN\"," +
            "      \"country_calling_code\": \"+48\"," +
            "      \"system_operator_address\": \"nextbike GmbH, Erich-Zeigner Allee 69-73, 04229 Leipzig, Germany\"," +
            "      \"country\": \"PL\"," +
            "      \"country_name\": \"Poland\"," +
            "      \"terms\": \"http://wroclawskirower.pl/regulamin/\"," +
            "      \"policy\": \"\"," +
            "      \"website\": \"http://www.wroclawskirower.pl/\"," +
            "      \"show_bike_types\": false," +
            "      \"show_bike_type_groups\": false," +
            "      \"show_free_racks\": false," +
            "      \"booked_bikes\": 0," +
            "      \"set_point_bikes\": 825," +
            "      \"available_bikes\": 565," +
            "      \"capped_available_bikes\": true," +
            "      \"pricing\": \"http://www.wroclawskirower.pl/\"," +
            "      \"cities\": [" +
            "        {" +
            "          \"uid\": 148," +
            "          \"lat\": 51.1097," +
            "          \"lng\": 17.0485," +
            "          \"zoom\": 13," +
            "          \"maps_icon\": \"\"," +
            "          \"alias\": \"wrocław\"," +
            "          \"break\": false," +
            "          \"name\": \"Wrocław\"," +
            "          \"num_places\": 77," +
            "          \"refresh_rate\": \"10120\"," +
            "          \"bounds\": {" +
            "            \"south_west\": {" +
            "              \"lat\": 51.048," +
            "              \"lng\": 16.9605" +
            "            }," +
            "            \"north_east\": {" +
            "              \"lat\": 51.136," +
            "              \"lng\": 17.1429" +
            "            }" +
            "          }," +
            "          \"booked_bikes\": 0," +
            "          \"set_point_bikes\": 775," +
            "          \"available_bikes\": 524," +
            "          \"return_to_official_only\": true," +
            "          \"bike_types\": {" +
            "            \"4\": 667" +
            "          }," +
            "          \"places\": [" +
            "            {" +
            "              \"uid\": 349478," +
            "              \"lat\": 51.132077147496," +
            "              \"lng\": 17.065501213074," +
            "              \"bike\": false," +
            "              \"name\": \"Pl. Kromera\"," +
            "              \"address\": null," +
            "              \"spot\": true," +
            "              \"number\": 5901," +
            "              \"bikes\": 5," +
            "              \"bike_racks\": 15," +
            "              \"free_racks\": 10," +
            "              \"maintenance\": false," +
            "              \"terminal_type\": \"\"," +
            "              \"bike_list\": [" +
            "                {" +
            "                  \"number\": \"57304\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57585\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57439\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57378\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57846\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }" +
            "              ]," +
            "              \"bike_numbers\": [" +
            "                \"57304\"," +
            "                \"57585\"," +
            "                \"57439\"," +
            "                \"57378\"," +
            "                \"57846\"" +
            "              ]," +
            "              \"bike_types\": {" +
            "                \"4\": 5" +
            "              }," +
            "              \"place_type\": \"0\"," +
            "              \"rack_locks\": true" +
            "            }," +
            "            {" +
            "              \"uid\": 349479," +
            "              \"lat\": 51.124542712045," +
            "              \"lng\": 17.034999132156," +
            "              \"bike\": false," +
            "              \"name\": \"Dworzec Nadodrze\"," +
            "              \"address\": null," +
            "              \"spot\": true," +
            "              \"number\": 5904," +
            "              \"bikes\": 1," +
            "              \"bike_racks\": 16," +
            "              \"free_racks\": 15," +
            "              \"maintenance\": false," +
            "              \"terminal_type\": \"\"," +
            "              \"bike_list\": [" +
            "                {" +
            "                  \"number\": \"57554\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }" +
            "              ]," +
            "              \"bike_numbers\": [" +
            "                \"57554\"" +
            "              ]," +
            "              \"bike_types\": {" +
            "                \"4\": 1" +
            "              }," +
            "              \"place_type\": \"0\"," +
            "              \"rack_locks\": true" +
            "            }," +
            "            {" +
            "              \"uid\": 349481," +
            "              \"lat\": 51.107233769858," +
            "              \"lng\": 17.061327695847," +
            "              \"bike\": false," +
            "              \"name\": \"Politechnika Wrocławska - Gmach Główny\"," +
            "              \"address\": null," +
            "              \"spot\": true," +
            "              \"number\": 5935," +
            "              \"bikes\": 5," +
            "              \"bike_racks\": 14," +
            "              \"free_racks\": 3," +
            "              \"maintenance\": false," +
            "              \"terminal_type\": \"\"," +
            "              \"bike_list\": [" +
            "                {" +
            "                  \"number\": \"57273\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57238\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57198\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57617\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }," +
            "                {" +
            "                  \"number\": \"57604\"," +
            "                  \"bike_type\": 4," +
            "                  \"lock_types\": [" +
            "                    \"analog_code_lock\"," +
            "                    \"rack_adapter\"" +
            "                  ]," +
            "                  \"active\": true," +
            "                  \"state\": \"ok\"," +
            "                  \"electric_lock\": false," +
            "                  \"boardcomputer\": 0" +
            "                }" +
            "              ]," +
            "              \"bike_numbers\": [" +
            "                \"57273\"," +
            "                \"57238\"," +
            "                \"57198\"," +
            "                \"57617\"," +
            "                \"57604\"" +
            "              ]," +
            "              \"bike_types\": {" +
            "                \"4\": 5" +
            "              }," +
            "              \"place_type\": \"0\"," +
            "              \"rack_locks\": true" +
            "            }" +
            "          ]" +
            "        }" +
            "      ]" +
            "    }," +
            "    {" +
            "      \"lat\": 52.2413," +
            "      \"lng\": 18.479," +
            "      \"zoom\": 6," +
            "      \"name\": \"Nextbike Poland\"," +
            "      \"hotline\": \"0048223821312\"," +
            "      \"domain\": \"np\"," +
            "      \"language\": \"pl\"," +
            "      \"email\": \"info@nextbike.pl\"," +
            "      \"timezone\": \"Europe/Warsaw\"," +
            "      \"currency\": \"PLN\"," +
            "      \"country_calling_code\": \"+48\"," +
            "      \"system_operator_address\": \"nextbike GmbH, Erich-Zeigner Allee 69-73, 04229 Leipzig, Germany\"," +
            "      \"country\": \"PL\"," +
            "      \"country_name\": \"Poland\"," +
            "      \"terms\": \"https://nextbike.pl/regulaminy/\"," +
            "      \"policy\": \"\"," +
            "      \"website\": \"http://www.nextbike.pl/\"," +
            "      \"show_bike_types\": false," +
            "      \"show_bike_type_groups\": false," +
            "      \"show_free_racks\": false," +
            "      \"booked_bikes\": 0," +
            "      \"set_point_bikes\": 1848," +
            "      \"available_bikes\": 2," +
            "      \"capped_available_bikes\": true," +
            "      \"pricing\": \"http://www.nextbike.pl/\"," +
            "      \"cities\": []" +
            "    }" +
            "  ]" +
            "}";
}