package org.mikolajczak.popularmovies.utils;

import org.junit.Test;
import org.mikolajczak.popularmovies.model.Movie;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ThemoviedbApiTest {
    String testJson = "{\"page\":1,\"total_results\":353105,\"total_pages\":17656,\"results\":" +
            "[{\"vote_count\":1126,\"id\":337167,\"video\":false,\"vote_average\":6.1,\"title\""
            + ":\"Fifty Shades Freed\",\"popularity\":542.892558," +
            "\"poster_path\":\"\\/jjPJ4s3DWZZvI4" + "vw8Xfi4Vqa1Q8.jpg\"," +
            "\"original_language\":\"en\",\"original_title\":\"Fifty Shades Freed\"" + "," +
            "\"genre_ids\":[18,10749],\"backdrop_path\":\"\\/9ywA15OAiwjSTvg3cBs9B7kOCBF.jpg\","
            + "\"adult\":false,\"overview\":\"Believing they have left behind shadowy figures " +
            "from " + "their past, newlyweds Christian and Ana fully embrace an inextricable " +
            "connection and " + "shared life of luxury. But just as she steps into her role as "
            + "Mrs. Grey and he relaxes" + " into an unfamiliar stability, new threats could " +
            "jeopardize their happy ending before " + "it even begins.\"," +
            "\"release_date\":\"2018-02-07\"},{\"vote_count\":6697,\"id\":269149,\"video\":false," +
            "" + "" + "" + "\"vote_average\":7.7,\"title\":\"Zootopia\"," +
            "\"popularity\":340.221253," + "\"poster_path\":\"\\/sM33SANp9z6rXW8Itn7NnG1GOEs" +
            ".jpg\",\"original_language\":\"en\"," + "" + "" + "\"original_title\":\"Zootopia\"," +
            "\"genre_ids\":[16,12,10751,35]," +
            "\"backdrop_path\":\"\\/mhdeE1yShHTaDbJVdWyTlzFvNkr.jpg\",\"adult\":false," +
            "\"overview\":\"Determined to prove herself, Officer Judy Hopps, the first bunny on "
            + "Zootopia's police force, jumps at the chance to crack her first case - even if it " +
            "" + "" + "" + "means partnering with scam-artist fox Nick Wilde to solve the mystery" +
            ".\"," + "\"release_date\":\"2016-02-11\"},{\"vote_count\":3477,\"id\":284054," +
            "\"video\":false," + "" + "\"vote_average\":7.4,\"title\":\"Black Panther\"," +
            "\"popularity\":308.317458," + "\"poster_path\":\"\\/uxzzxijgPIY7slzFvMotPv8wjKA" + "" +
            ".jpg\",\"original_language\":\"en\"," + "" + "\"original_title\":\"Black Panther\","
            + "\"genre_ids\":[28,12,14,878]," +
            "\"backdrop_path\":\"\\/b6ZJZHUdMEFECvGiDpJjlfUWela" + ".jpg\",\"adult\":false," +
            "\"overview\":\"King T'Challa returns home from America " + "to the reclusive, " +
            "technologically advanced African nation of Wakanda to serve " + "as his country's " +
            "new " + "leader. However, T'Challa soon finds that he is challenged" + " for the " +
            "throne by " + "factions within his own country as well as without.  Using " +
            "powers reserved to " + "Wakandan kings, T'Challa assumes the Black Panther mantel " +
            "to" + " join with girlfriend " + "Nakia, the queen-mother, his princess-kid sister, " +
            " " + "members of the Dora Milaje (the" + " " + "Wakandan \\\"special forces\\\"), " +
            "and an " + "American secret agent, to prevent " + "Wakanda " + "from being dragged " +
            "into a world " + "war.\"," + "\"release_date\":\"2018-02-13\"}," +
            "{\"vote_count\":588,\"id\":338970," + "" + "\"video\":false,\"vote_average\":6.1," +
            "\"title\":\"Tomb Raider\"," + "\"popularity\":201.127325," +
            "\"poster_path\":\"\\/ePyN2nX9t8SOl70eRW47Q29zUFO" + "" + ".jpg\"," +
            "\"original_language\":\"en\"," + "\"original_title\":\"Tomb Raider\"," +
            "\"genre_ids\":[28,12]," + "\"backdrop_path\":\"\\/bLJTjfbZ1c5zSNiAvGYs1Uc82ir.jpg\"," +
            "" + "" + "\"adult\":false," + "\"overview\":\"Lara Croft, the fiercely independent "
            + "daughter of" + " a missing " + "adventurer, must push herself beyond her limits " +
            "when" + " she finds " + "herself on the island" + " where her father disappeared.\"," +
            "" + "\"release_date\":\"2018-03-08\"}," + "{\"vote_count\":3434,\"id\":354912," +
            "\"video\":false,\"vote_average\":7.8," + "\"title\":\"Coco\"," +
            "\"popularity\":198.585885," + "\"poster_path\":\"\\/eKi8dIrr8voobbaGzDpe8w0PVbC" +
            "" + ".jpg\",\"original_language\":\"en\"," + "\"original_title\":\"Coco\"," +
            "\"genre_ids\":[12,35,10751,16]," +
            "\"backdrop_path\":\"\\/askg3SMvhqEl4OL52YuvdtY40Yb.jpg\",\"adult\":false," +
            "\"overview\":\"Despite his family’s baffling generations-old ban on music, Miguel "
            + "dreams of becoming an accomplished musician like his idol, Ernesto de la Cruz. " +
            "Desperate to prove his talent, Miguel finds himself in the stunning and colorful " +
            "Land of the Dead following a mysterious chain of events. Along the way, he meets " +
            "charming trickster Hector, and together, they set off on an extraordinary journey "
            + "to" + " unlock the real story behind Miguel's family history.\"," +
            "\"release_date\":\"2017-10-27\"},{\"vote_count\":4570,\"id\":181808,\"video\":false," +
            "" + "" + "" + "\"vote_average\":7.1,\"title\":\"Star Wars: The Last Jedi\"," +
            "\"popularity\":183.418378,\"poster_path\":\"\\/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg\"," +
            "\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\"," +
            "\"genre_ids\":[28,14,12,878],\"backdrop_path\":\"\\/c4Dw37VZjBmObmJw9bmt8IDwMZH" +
            "" + ".jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered " +
            "abilities " + "with the guidance of Luke Skywalker, who is unsettled by the " +
            "strength" + " of her " + "powers." + " Meanwhile, the Resistance prepares to do " +
            "battle with the " + "First Order" + ".\"," + "\"release_date\":\"2017-12-13\"}," +
            "{\"vote_count\":5169," + "\"id\":284053," + "\"video\":false," +
            "\"vote_average\":7.4,\"title\":\"Thor: " + "Ragnarok\"," +
            "\"popularity\":151.021614," + "\"poster_path\":\"\\/rzRwTcFvttcN1ZpX2xv4j3tSdJu" + "" +
            ".jpg\"," + "\"original_language\":\"en\"," + "\"original_title\":\"Thor: Ragnarok\"," +
            "" + "\"genre_ids\":[28,12,14]," +
            "\"backdrop_path\":\"\\/kaIfm5ryEOwYg8mLbq8HkPuM1Fo" + ".jpg\",\"adult\":false," +
            "\"overview\":\"Thor is imprisoned on the other side of " + "the universe and finds "
            + "himself in a race against time to get back to Asgard " + "to " + "stop Ragnarok, " +
            "the prophecy " + "of destruction to his homeworld and the end" + " of " + "Asgardian" +
            " civilization, at the hands " + "of an all-powerful new threat, " + "the " +
            "ruthless Hela.\"," + "\"release_date\":\"2017-10-25\"}," + "{\"vote_count\":1068," +
            "\"id\":300668,\"video\":false," + "\"vote_average\":6.4," +
            "\"title\":\"Annihilation\"," + "\"popularity\":129.015573," +
            "\"poster_path\":\"\\/d3qcpfNwbAMCNqWDHzPQsUYiUgS" + ".jpg\"," +
            "\"original_language\":\"en\"," + "\"original_title\":\"Annihilation\"," +
            "\"genre_ids\":[9648,878,18]," + "\"backdrop_path\":\"\\/5zfVNTrkhMu673zma6qhFzG01ig"
            + ".jpg\",\"adult\":false," + "\"overview\":\"A biologist signs up for a dangerous, "
            + "secret expedition into a " + "mysterious zone where the laws of nature don't " +
            "apply" + ".\"," + "\"release_date\":\"2018-02-22\"},{\"vote_count\":2991," +
            "\"id\":399055," + "\"video\":false," + "\"vote_average\":7.3,\"title\":\"The Shape "
            + "of Water\"," + "\"popularity\":125.385207," +
            "\"poster_path\":\"\\/k4FwHlMhuRR5BISY2Gm2QZHlH5Q" + ".jpg\"," +
            "\"original_language\":\"en\"," + "\"original_title\":\"The Shape of Water\"," +
            "\"genre_ids\":[18,14,10749]," +
            "\"backdrop_path\":\"\\/rgyhSn3mINvkuy9iswZK0VLqQO3.jpg\",\"adult\":false," +
            "\"overview\":\"An other-worldly story, set against the backdrop of Cold War era " +
            "America circa 1962, where a mute janitor working at a lab falls in love with an " +
            "amphibious man being held captive there and devises a plan to help him escape.\"," +
            "\"release_date\":\"2017-12-01\"},{\"vote_count\":3825,\"id\":141052,\"video\":false," +
            "" + "" + "" + "\"vote_average\":6.4,\"title\":\"Justice League\"," +
            "\"popularity\":117.800872," + "\"poster_path\":\"\\/eifGNCSDuxJeS1loAXil5bIGgvC" + "" +
            ".jpg\",\"original_language\":\"en\"," + "" + "\"original_title\":\"Justice League\"," +
            "" + "\"genre_ids\":[28,12,14,878]," +
            "\"backdrop_path\":\"\\/o5T8rZxoWSBMYwjsUFUqTt6uMQB" + ".jpg\",\"adult\":false," +
            "\"overview\":\"Fuelled by his restored faith in humanity" + " and inspired by " +
            "Superman's " + "" + "selfless act, Bruce Wayne and Diana Prince " + "assemble a team" +
            " of metahumans " + "consisting " + "of Barry Allen, Arthur Curry and " + "Victor " +
            "Stone to face the " + "catastrophic threat of " + "Steppenwolf and the " +
            "Parademons who are on the hunt for " + "three Mother Boxes on Earth" + ".\"," +
            "\"release_date\":\"2017-11-15\"}," + "{\"vote_count\":7746,\"id\":321612," +
            "\"video\":false,\"vote_average\":6.8," + "\"title\":\"Beauty and the Beast\"," +
            "\"popularity\":115.015256," + "\"poster_path\":\"\\/tWqifoYuwLETmmasnGHO7xBjEtt" + "" +
            ".jpg\"," + "\"original_language\":\"en\",\"original_title\":\"Beauty and the " +
            "Beast\"," + "\"genre_ids\":[10751,14,10749]," +
            "\"backdrop_path\":\"\\/6aUWe0GSl69wMTSWWexsorMIvwU" + ".jpg\",\"adult\":false," +
            "\"overview\":\"A live-action adaptation of Disney's version " + "of the classic " +
            "tale" + " of a cursed prince and a beautiful young woman who helps him " + "break " +
            "the spell" + ".\",\"release_date\":\"2017-03-16\"},{\"vote_count\":7383," +
            "\"id\":198663," + "\"video\":false,\"vote_average\":7,\"title\":\"The Maze Runner\"," +
            "" + "\"popularity\":110.905007,\"poster_path\":\"\\/coss7RgL0NH6g4fC2s5atvf3dFO" +
            ".jpg\"," + "\"original_language\":\"en\",\"original_title\":\"The Maze Runner\"," +
            "\"genre_ids\":[28,9648,878,53]," +
            "\"backdrop_path\":\"\\/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg\",\"adult\":false," +
            "\"overview\":\"Set in a post-apocalyptic world, young Thomas is deposited in a " +
            "community of boys after his memory is erased, soon learning they're all trapped in "
            + "a" + " maze that will require him to join forces with fellow “runners” for a shot " +
            "" + "at" + " " + "escape.\",\"release_date\":\"2014-09-10\"},{\"vote_count\":191," +
            "\"id\":268896," + "\"video\":false,\"vote_average\":6.2,\"title\":\"Pacific Rim: " +
            "Uprising\"," + "\"popularity\":106.417094," +
            "\"poster_path\":\"\\/v5HlmJK9bdeHxN2QhaFP1ivjX3U.jpg\"," +
            "\"original_language\":\"en\",\"original_title\":\"Pacific Rim: Uprising\"," +
            "\"genre_ids\":[28,14,878,12],\"backdrop_path\":\"\\/jj8qgyrfQ12ZLZSY1PEbA3FRkfY" +
            "" + ".jpg\",\"adult\":false,\"overview\":\"It has been ten years since The Battle " +
            "of" + " the " + "" + "Breach and the oceans are still, but restless. Vindicated by " +
            "the " + "victory at the" + " " + "Breach, the Jaeger program has evolved into the " +
            "most " + "powerful global defense " + "force " + "in human history. The PPDC now " +
            "calls upon " + "the best and brightest to rise " + "up and " + "become the next " +
            "generation of heroes" + " when the Kaiju threat returns.\"," +
            "\"release_date\":\"2018-03-21\"}," + "{\"vote_count\":3859,\"id\":335984," +
            "\"video\":false," + "\"vote_average\":7.3," + "\"title\":\"Blade Runner 2049\"," +
            "\"popularity\":103.624697," +
            "\"poster_path\":\"\\/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg\",\"original_language\":\"en\"," +
            "" + "" + "" + "\"original_title\":\"Blade Runner 2049\",\"genre_ids\":[9648,878,53]," +
            "" + "\"backdrop_path\":\"\\/zfWPeRgYpRjPZLGwwkfnTfaFnNh.jpg\",\"adult\":false," +
            "\"overview\":\"Thirty years after the events of the first film, a new blade runner, " +
            "" + "" + "" + "LAPD Officer K, unearths a long-buried secret that has the potential " +
            "to " + "plunge " + "" + "what's left of society into chaos. K's discovery leads him " +
            "on a " + "quest to find " + "Rick " + "Deckard, a former LAPD blade runner who has " +
            "been " + "missing for 30 years.\"," + "\"release_date\":\"2017-10-04\"}," +
            "{\"vote_count\":12198," + "\"id\":118340," + "\"video\":false,\"vote_average\":7.9," +
            "\"title\":\"Guardians of the" + " Galaxy\"," + "\"popularity\":98.832164," +
            "\"poster_path\":\"\\/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg\"," +
            "\"original_language\":\"en\",\"original_title\":\"Guardians of the Galaxy\"," +
            "\"genre_ids\":[28,878,12],\"backdrop_path\":\"\\/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\","
            + "\"adult\":false,\"overview\":\"Light years from Earth, 26 years after being " +
            "abducted, Peter Quill finds himself the prime target of a manhunt after discovering " +
            "" + "" + "" + "an orb wanted by Ronan the Accuser.\"," +
            "\"release_date\":\"2014-07-30\"}," + "{\"vote_count\":3138,\"id\":353486," +
            "\"video\":false,\"vote_average\":6.5," + "\"title\":\"Jumanji: Welcome to the " +
            "Jungle\",\"popularity\":96.651904," +
            "\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\"," +
            "" + "" + "" + "\"original_title\":\"Jumanji: Welcome to the Jungle\"," +
            "\"genre_ids\":[28," + "12,35," + "10751]," +
            "\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\"," + "\"adult\":false," +
            "\"overview\":\"The tables are turned as four teenagers are " + "sucked into " +
            "Jumanji's " + "world - pitted against rhinos, black mambas and an " + "endless " +
            "variety of jungle traps" + " " + "and puzzles. To survive, they'll play as " +
            "characters from the game.\"," + "\"release_date\":\"2017-12-09\"}," +
            "{\"vote_count\":280,\"id\":347882,\"video\":false," + "\"vote_average\":5.2," +
            "\"title\":\"Sleight\",\"popularity\":92.990156," +
            "\"poster_path\":\"\\/wridRvGxDqGldhzAIh3IcZhHT5F.jpg\",\"original_language\":\"en\"," +
            "" + "" + "" + "\"original_title\":\"Sleight\",\"genre_ids\":[18,53,28,878]," +
            "\"backdrop_path\":\"\\/2SEgJ0mHJ7TSdVDbkGU061tR33K.jpg\",\"adult\":false," +
            "\"overview\":\"A young street magician is left to take care of his little sister " +
            "after his mother's passing and turns to drug dealing in the Los Angeles party scene " +
            "" + "" + "" + "to keep a roof over their heads. When he gets into trouble with his "
            + "supplier, " + "his " + "sister is kidnapped and he is forced to rely on both his "
            + "sleight of hand " + "and " + "brilliant mind to save her.\"," +
            "\"release_date\":\"2017-04-28\"}," + "{\"vote_count\":15," + "\"id\":499772," +
            "\"video\":false,\"vote_average\":5.4," + "\"title\":\"Meet Me In St. " + "Gallen\","
            + "\"popularity\":86.179569," + "\"poster_path\":\"\\/uGntNjUx6YAzbVy7RDgxWnWsdet" +
            "" + ".jpg\"," + "\"original_language\":\"tl\",\"original_title\":\"Meet Me In St. "
            + "Gallen\"," + "\"genre_ids\":[18,10749]," +
            "\"backdrop_path\":\"\\/4QrbczSQGZQA7BG9xMhccQI7LHm.jpg\"," + "\"adult\":false," +
            "\"overview\":\"The story of Jesse and Celeste who meets at an " + "unexpected time "
            + "in their lives. They then realize their names are the same as the " + "characters " +
            "in" + " the popular break-up romantic comedy, Celeste and Jesse Forever.\"," +
            "\"release_date\":\"2018-02-07\"},{\"vote_count\":541,\"id\":460793," +
            "\"video\":false," + "\"vote_average\":5.9,\"title\":\"Olaf's Frozen Adventure\"," +
            "\"popularity\":77.812109," + "\"poster_path\":\"\\/As8WTtxXs9e3cBit3ztTf7zoRmm" + ""
            + ".jpg\",\"original_language\":\"en\"," + "\"original_title\":\"Olaf's Frozen " +
            "Adventure\",\"genre_ids\":[12,16,35,10751,14," + "10402]," +
            "\"backdrop_path\":\"\\/9K4QqQZg4TVXcxBGDiVY4Aey3Rn.jpg\",\"adult\":false," +
            "\"overview\":\"Olaf is on a mission to harness the best holiday traditions for Anna," +
            "" + "" + "" + " Elsa, and Kristoff.\",\"release_date\":\"2017-10-27\"}," +
            "{\"vote_count\":13924," + "\"id\":24428,\"video\":false,\"vote_average\":7.5," +
            "\"title\":\"The Avengers\"," + "\"popularity\":76.003551," +
            "\"poster_path\":\"\\/cezWGskPY5x7GaglTTRN4Fugfb8.jpg\"," +
            "\"original_language\":\"en\",\"original_title\":\"The Avengers\",\"genre_ids\":[878," +
            "" + "" + "" + "28,12],\"backdrop_path\":\"\\/hbn46fQaRmlpBuUrEiFqv0GDL6Y.jpg\"," +
            "\"adult\":false," + "\"overview\":\"When an unexpected enemy emerges and threatens "
            + "global safety and " + "security, Nick Fury, director of the international " +
            "peacekeeping agency known as S.H" + ".I.E.L.D., finds himself in need of a team to "
            + "pull the world back from the brink of " + "disaster. Spanning the globe, a daring " +
            "" + "" + "recruitment effort begins!\"," + "\"release_date\":\"2012-04-25\"}]}";

    public void testMovieArrayList(ArrayList<Movie> movies) {
        assertNotNull("method should't return null", movies);
        assertEquals("method should return 20 elements", 20, movies.size());

        // Check if every of values is not null
        for (int i = 0; i < movies.size(); i++) {
            assertNotNull("Any of movie's value should't be null", movies.get(i).getPlotSynopsis());
            assertNotNull("Any of movie's value should't be null", movies.get(i).getPoster());
            assertNotNull("Any of movie's value should't be null", movies.get(i).getReleaseDate());
            assertNotNull("Any of movie's value should't be null", movies.get(i).getTitle());
            assertNotNull("Any of movie's value should't be null", movies.get(i).getVoteAvg());
        }
    }

    @Test
    public void getMoviesFromJson() {
        ArrayList<Movie> movies = ThemoviedbApi.getMoviesFromJson(testJson);
        testMovieArrayList(movies);
    }
}