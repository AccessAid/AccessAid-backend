package dev.accessaid.AccessAid.config.documentation.Places;

import com.google.maps.model.Photo;

public class ExamplesValues_Photos {

        public static final String HEIGHT = "749";

        public static final String HEIGHT2 = "3024";

        public static final String[] HTML_ATTRIBUTIONS = {
                        "<a href=\"https://maps.google.com/maps/contrib/116267039673220225154\">Australian Cruise Group</a>"
        };

        public static final String HTML_ATTRIBUTIONS_STRING = "[ "
                        + "<a href=\"https://maps.google.com/maps/contrib/116267039673220225154\">Australian Cruise Group</a>"
                        + "]";

        public static final String[] HTML_ATTRIBUTIONS2 = {
                        "<a href=\"https://maps.google.com/maps/contrib/109154317687146483280\">Christine Neuville</a>"
        };

        public static final String PHOTO_REFERENCE = "AZose0mkOXVdfoctN2-G6cix52eD3FlLiYtbcg8USHKBVZPiYgCJWfMp9D0JLRa3eEAoqKOVILRzZ_oKdobh1M36Xz7hK_hfLIvNGbGdQow4vv4HJBygiU_dyQV1SWzsX3tOdf9m8IMKJRGCKUJX2OWUS34oCueEOOXuWKyx2YQo0HUF8fWK";

        public static final String PHOTO_REFERENCE2 = "AZose0nx22kjN3CDjzo_K3wzFK-0wp0RsgWC1AT8ObxGHEXUssTIuQiV6z722_3lSOB8zR6xrKCoGxoYqLLwspBA3g3oGbrxGzZ13Q0uxuJBoSti3puqWJOMPTvhSgKseBxYp_TzB9s326eYuKlMP86bSdDd_cSy1AOEG3wzBD9wO026aOQ";

        public static final String WIDTH = "1000";

        public static final String WIDTH2 = "4032";

        public static final Photo[] PLACE_PHOTOS = {
                        createPhoto(PHOTO_REFERENCE, Integer.parseInt(HEIGHT), Integer.parseInt(WIDTH),
                                        HTML_ATTRIBUTIONS),
                        createPhoto(PHOTO_REFERENCE2, Integer.parseInt(HEIGHT2), Integer.parseInt(WIDTH2),
                                        HTML_ATTRIBUTIONS2)
        };

        public static final String PLACE_PHOTOS_STRING = "[\n" +
                        "  {\n" +
                        "    \"photoReference\": \"AZose0mkOXVdfoctN2-G6cix52eD3FlLiYtbcg8USHKBVZPiYgCJWfMp9D0JLRa3eEAoqKOVILRzZ_oKdobh1M36Xz7hK_hfLIvNGbGdQow4vv4HJBygiU_dyQV1SWzsX3tOdf9m8IMKJRGCKUJX2OWUS34oCueEOOXuWKyx2YQo0HUF8fWK\",\n"
                        +
                        "    \"height\": 749,\n" +
                        "    \"width\": 1000,\n" +
                        "    \"htmlAttributions\": []\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"photoReference\": \"AZose0nx22kjN3CDjzo_K3wzFK-0wp0RsgWC1AT8ObxGHEXUssTIuQiV6z722_3lSOB8zR6xrKCoGxoYqLLwspBA3g3oGbrxGzZ13Q0uxuJBoSti3puqWJOMPTvhSgKseBxYp_TzB9s326eYuKlMP86bSdDd_cSy1AOEG3wzBD9wOQ\",\n"
                        +
                        "    \"height\": 3024,\n" +
                        "    \"width\": 4032,\n" +
                        "    \"htmlAttributions\": []\n" +
                        "  }\n" +
                        "]";

        private static Photo createPhoto(String photoReference, int height, int width, String[] htmlAttributions) {
                Photo photo = new Photo();
                photo.photoReference = photoReference;
                photo.height = height;
                photo.width = width;
                photo.htmlAttributions = htmlAttributions;
                return photo;
        }
}
