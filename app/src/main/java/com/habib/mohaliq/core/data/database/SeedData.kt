package com.habib.mohaliq.core.data.database

import com.habib.mohaliq.core.data.database.entity.MenuItemEntity
import com.habib.mohaliq.core.data.database.entity.PlaceEntity
import com.habib.mohaliq.core.data.database.entity.RoomOptionEntity
import com.habib.mohaliq.core.model.PlaceType

/**
 * Curated content for the local database.
 *
 * Content is inserted once when the database is first created.
 */
object SeedData {

    private fun unsplash(id: String) =
        "https://images.unsplash.com/$id?w=800&q=80&auto=format&fit=crop"

    val places = listOf(

        // ---------------------------------------------------------------------
        // Hotels
        // ---------------------------------------------------------------------

        PlaceEntity(
            id = "hotel-neano",
            type = PlaceType.HOTEL.name,
            title = "Neano Escape Hotel",
            location = "Manggis, Bali",
            description = "A quiet eco-retreat tucked into the jungle outside Manggis, built around " +
                    "the idea of slowing down — private garden villas, an infinity pool facing the " +
                    "treeline, and no televisions in the rooms.",
            rating = 4.7f,
            reviewCount = 8186,
            currentPrice = 112.0,
            badge = "5 Star",
            imageUrl = unsplash("photo-1414510451013-d0a41fea512e"),
            latitude = -8.4456,
            longitude = 115.5991
        ),

        PlaceEntity(
            id = "hotel-viceroy",
            type = PlaceType.HOTEL.name,
            title = "Viceroy Bali",
            location = "Ubud, Bali",
            description = "A luxury hillside resort above the Petanu River valley, known for private " +
                    "pool villas and panoramic jungle views — one of Ubud's most recognized high-end stays.",
            rating = 4.5f,
            reviewCount = 4136,
            currentPrice = 149.0,
            originalPrice = 169.0,
            badge = "5 Star",
            imageUrl = unsplash("photo-1610641818989-c2051b5e2cfd"),
            latitude = -8.4816,
            longitude = 115.2661
        ),

        PlaceEntity(
            id = "hotel-amnaya",
            type = PlaceType.HOTEL.name,
            title = "Amnaya Resort",
            location = "Nusa Dua, Bali",
            description = "A beachfront resort on Nusa Dua's calm coastline, built around a large " +
                    "pool that runs straight to the ocean view — family-friendly with easy beach access.",
            rating = 4.9f,
            reviewCount = 4348,
            currentPrice = 90.5,
            badge = "5 Star",
            imageUrl = unsplash("photo-1520250497591-112f2f40a3f4"),
            latitude = -8.7997,
            longitude = 115.2247
        ),

        PlaceEntity(
            id = "hotel-sawah-indah",
            type = PlaceType.HOTEL.name,
            title = "Sawah Indah Resort",
            location = "Tabanan, Bali",
            description = "A boutique resort set among Tabanan's rice terraces, with a small pool " +
                    "deck and open-air lounge looking out over the paddies.",
            rating = 4.6f,
            reviewCount = 2140,
            currentPrice = 98.0,
            badge = "10% OFF",
            imageUrl = unsplash("photo-1562790351-d273a961e0e9"),
            latitude = -8.5385,
            longitude = 115.1256
        ),

        PlaceEntity(
            id = "hotel-azure-ubud",
            type = PlaceType.HOTEL.name,
            title = "Azure Ubud Retreat",
            location = "Ubud, Bali",
            description = "A peaceful tropical retreat surrounded by the lush greenery of Ubud, " +
                    "featuring a tranquil pool, comfortable accommodations, and a relaxed atmosphere " +
                    "designed for travelers seeking a quiet escape close to Bali's cultural heart.",
            rating = 4.8f,
            reviewCount = 3264,
            currentPrice = 128.0,
            badge = "5 Star",
            imageUrl = unsplash("photo-1725454918208-6583f4bfcbed"),
            latitude = -8.5069,
            longitude = 115.2625
        ),

        PlaceEntity(
            id = "hotel-nusa-haven",
            type = PlaceType.HOTEL.name,
            title = "Nusa Haven Resort",
            location = "Nusa Dua, Bali",
            description = "A modern resort in Nusa Dua offering a relaxing tropical setting, spacious " +
                    "accommodations, and a large pool surrounded by greenery, providing an easy base " +
                    "for exploring Bali's southern coastline.",
            rating = 4.7f,
            reviewCount = 2876,
            currentPrice = 136.0,
            originalPrice = 152.0,
            badge = "10% OFF",
            imageUrl = unsplash("photo-1681864143679-e2f031338615"),
            latitude = -8.8001,
            longitude = 115.2270
        ),

        PlaceEntity(
            id = "hotel-ubud-garden",
            type = PlaceType.HOTEL.name,
            title = "Ubud Garden Villas",
            location = "Ubud, Bali",
            description = "A charming villa-style retreat surrounded by tropical gardens in Ubud, " +
                    "combining private accommodation with a peaceful pool area and easy access to " +
                    "the area's rice fields, cafes, and cultural attractions.",
            rating = 4.6f,
            reviewCount = 1984,
            currentPrice = 104.0,
            badge = "4 Star",
            imageUrl = unsplash("photo-1548386704-23fc0135faab"),
            latitude = -8.5064,
            longitude = 115.2612
        ),

        // ---------------------------------------------------------------------
        // Restaurants
        // ---------------------------------------------------------------------

        PlaceEntity(
            id = "restaurant-kampung-cafe",
            type = PlaceType.RESTAURANT.name,
            title = "The Kampung Cafe",
            location = "Ubud, Bali",
            description = "A cozy cafe serving traditional Balinese dishes with a modern twist, " +
                    "set among rice paddies with an open-air dining area.",
            rating = 4.8f,
            reviewCount = 962,
            currentPrice = 8.5,
            imageUrl = unsplash("photo-1610049455204-15837fe00de0"),
            latitude = -8.5069,
            longitude = 115.2625
        ),

        PlaceEntity(
            id = "restaurant-ubud-spice",
            type = PlaceType.RESTAURANT.name,
            title = "Ubud Spice House",
            location = "Ubud, Bali",
            description = "A cozy Ubud restaurant serving Indonesian-inspired dishes with fresh " +
                    "local ingredients, combining traditional flavors with a relaxed dining atmosphere " +
                    "surrounded by the greenery of Bali.",
            rating = 4.8f,
            reviewCount = 1486,
            currentPrice = 9.0,
            imageUrl = unsplash("photo-1535923633864-cbf229ad891c"),
            latitude = -8.5072,
            longitude = 115.2628
        ),

        PlaceEntity(
            id = "restaurant-ubud-green",
            type = PlaceType.RESTAURANT.name,
            title = "Ubud Green Kitchen",
            location = "Ubud, Bali",
            description = "A relaxed plant-forward restaurant in Ubud serving fresh Indonesian-inspired " +
                    "dishes, colorful vegetable plates, rice bowls, and tropical drinks in a casual " +
                    "garden setting.",
            rating = 4.8f,
            reviewCount = 1684,
            currentPrice = 10.0,
            imageUrl = unsplash("photo-1535923633864-cbf229ad891c"),
            latitude = -8.5075,
            longitude = 115.2630
        ),

        PlaceEntity(
            id = "restaurant-canggu-harvest",
            type = PlaceType.RESTAURANT.name,
            title = "Canggu Harvest Table",
            location = "Canggu, Bali",
            description = "A modern Canggu eatery focused on wholesome Indonesian and Southeast " +
                    "Asian comfort food, with vegetable-rich rice dishes, fresh salads, and naturally " +
                    "sweet tropical desserts.",
            rating = 4.7f,
            reviewCount = 1932,
            currentPrice = 11.0,
            imageUrl = unsplash("photo-1522036664039-3c5756c2b459"),
            latitude = -8.6478,
            longitude = 115.1385
        ),

        PlaceEntity(
            id = "restaurant-warung-bambu",
            type = PlaceType.RESTAURANT.name,
            title = "Warung Bambu",
            location = "Ubud, Bali",
            description = "A traditional warung built from bamboo, serving home-style Balinese dishes " +
                    "in a lush garden setting.",
            rating = 4.6f,
            reviewCount = 1320,
            currentPrice = 7.0,
            imageUrl = unsplash("photo-1693164481148-f574bec4423b"),
            latitude = -8.51,
            longitude = 115.26
        ),

        PlaceEntity(
            id = "restaurant-seminyak-garden",
            type = PlaceType.RESTAURANT.name,
            title = "Seminyak Garden Kitchen",
            location = "Seminyak, Bali",
            description = "A modern Indonesian kitchen with open-air garden seating, known for its " +
                    "grilled fish and relaxed atmosphere.",
            rating = 4.7f,
            reviewCount = 2005,
            currentPrice = 10.0,
            imageUrl = unsplash("photo-1773327275715-d3b43141fa29"),
            latitude = -8.6906,
            longitude = 115.1656
        ),

        // ---------------------------------------------------------------------
        // Destinations
        // ---------------------------------------------------------------------

        PlaceEntity(
            id = "destination-tegalalang",
            type = PlaceType.DESTINATION.name,
            title = "Tegallalang Rice Terrace",
            location = "Ubud, Bali",
            description = "A spectacular landscape of layered rice terraces north of Ubud, surrounded " +
                    "by tropical greenery and traditional agricultural scenery, offering one of Bali's " +
                    "most recognizable countryside views.",
            rating = 4.8f,
            reviewCount = 5420,
            currentPrice = 0.0,
            badge = "Rice Terraces",
            imageUrl = unsplash("photo-1680100595862-9c8803a9e7da"),
            latitude = -8.4312,
            longitude = 115.2792
        ),

        PlaceEntity(
            id = "destination-kelingking",
            type = PlaceType.DESTINATION.name,
            title = "Kelingking Beach",
            location = "Nusa Penida, Bali",
            description = "A dramatic clifftop viewpoint over a T-Rex-shaped headland and turquoise " +
                    "bay — one of the most photographed views in Bali.",
            rating = 4.9f,
            reviewCount = 6540,
            currentPrice = 0.0,
            badge = "Free entry",
            imageUrl = unsplash("photo-1533393492471-bd487fd86eac"),
            latitude = -8.7515,
            longitude = 115.4681
        ),

        PlaceEntity(
            id = "destination-banyu-wana",
            type = PlaceType.DESTINATION.name,
            title = "Banyu Wana Amertha Waterfall",
            location = "Buleleng, Bali",
            description = "A tucked-away waterfall in North Bali, reached by a short jungle walk — " +
                    "far quieter than Bali's better-known waterfalls.",
            rating = 4.6f,
            reviewCount = 1180,
            currentPrice = 2.0,
            badge = "$2 entry",
            imageUrl = unsplash("photo-1695611280324-a5f9b8e8594e"),
            latitude = -8.2415,
            longitude = 115.0857
        ),

        PlaceEntity(
            id = "destination-sekumpul",
            type = PlaceType.DESTINATION.name,
            title = "Sekumpul Waterfall",
            location = "Buleleng, Bali",
            description = "A dramatic collection of waterfalls surrounded by dense tropical jungle in " +
                    "North Bali, known for its lush scenery, powerful cascades, and peaceful natural " +
                    "atmosphere away from the busier southern resorts.",
            rating = 4.9f,
            reviewCount = 6180,
            currentPrice = 0.0,
            badge = "Waterfall",
            imageUrl = unsplash("photo-1775829177029-5d2635a8174f"),
            latitude = -8.1720,
            longitude = 115.1830
        ),

        PlaceEntity(
            id = "destination-mount-batur",
            type = PlaceType.DESTINATION.name,
            title = "Mount Batur",
            location = "Kintamani, Bali",
            description = "An active volcano in Bali's highlands famous for its sunrise views, volcanic " +
                    "landscape, and panoramic scenery over Lake Batur and the surrounding mountains.",
            rating = 4.8f,
            reviewCount = 5220,
            currentPrice = 0.0,
            badge = "Sunrise trek",
            imageUrl = unsplash("photo-1505203762024-6d9eee62e61a"),
            latitude = -8.2422,
            longitude = 115.3752
        )
    )

    // -------------------------------------------------------------------------
    // Hotel rooms
    // -------------------------------------------------------------------------

    val roomOptions = listOf(
        // -------------------------------------------------------------------------
        // Neano Escape Hotel
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-neano-deluxe",
            hotelId = "hotel-neano",
            title = "Deluxe Room",
            price = 112.0,
            breakfastIncluded = false,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1562438668-bcf0ca6578f0")
        ),

        RoomOptionEntity(
            id = "room-neano-suite",
            hotelId = "hotel-neano",
            title = "Suite Room",
            price = 149.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1618773928121-c32242e63f39")
        ),

        // -------------------------------------------------------------------------
        // Viceroy Bali
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-viceroy-deluxe-pool-villa",
            hotelId = "hotel-viceroy",
            title = "Deluxe Pool Villa",
            price = 149.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1654482278660-14a8cfd5589d")
        ),

        RoomOptionEntity(
            id = "room-viceroy-royal-villa",
            hotelId = "hotel-viceroy",
            title = "Royal Villa",
            price = 189.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1760942966693-e5db8bf90060")
        ),

        // -------------------------------------------------------------------------
        // Amnaya Resort
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-amnaya-deluxe-ocean",
            hotelId = "hotel-amnaya",
            title = "Deluxe Ocean Room",
            price = 90.5,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1680210851458-b7dc5685e06e")
        ),

        RoomOptionEntity(
            id = "room-amnaya-ocean-suite",
            hotelId = "hotel-amnaya",
            title = "Ocean View Suite",
            price = 125.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1725962479542-1be0a6b0d444")
        ),

        // -------------------------------------------------------------------------
        // Sawah Indah Resort
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-sawah-rice-terrace",
            hotelId = "hotel-sawah-indah",
            title = "Rice Terrace Room",
            price = 98.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1667125095636-dce94dcbdd96")
        ),

        RoomOptionEntity(
            id = "room-sawah-garden-villa",
            hotelId = "hotel-sawah-indah",
            title = "Garden Villa",
            price = 128.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1743410976099-6114097db9ba")
        ),

        // -------------------------------------------------------------------------
        // Azure Ubud Retreat
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-azure-garden-deluxe",
            hotelId = "hotel-azure-ubud",
            title = "Garden Deluxe Room",
            price = 128.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1566665797739-1674de7a421a")
        ),

        RoomOptionEntity(
            id = "room-azure-private-pool",
            hotelId = "hotel-azure-ubud",
            title = "Private Pool Villa",
            price = 175.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1760943006664-3278c14068d6")
        ),

        // -------------------------------------------------------------------------
        // Nusa Haven Resort
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-nusa-deluxe-garden",
            hotelId = "hotel-nusa-haven",
            title = "Deluxe Garden Room",
            price = 136.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1631049552057-403cdb8f0658")
        ),

        RoomOptionEntity(
            id = "room-nusa-premium-pool",
            hotelId = "hotel-nusa-haven",
            title = "Premium Pool Suite",
            price = 165.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1578683010236-d716f9a3f461")
        ),

        // -------------------------------------------------------------------------
        // Ubud Garden Villas
        // -------------------------------------------------------------------------

        RoomOptionEntity(
            id = "room-ubud-garden-deluxe",
            hotelId = "hotel-ubud-garden",
            title = "Garden Deluxe Room",
            price = 104.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1512918728675-ed5a9ecdebfd")
        ),

        RoomOptionEntity(
            id = "room-ubud-garden-villa",
            hotelId = "hotel-ubud-garden",
            title = "Garden Villa",
            price = 139.0,
            breakfastIncluded = true,
            cancellationPolicy = "Free cancellation until 48 hours before check-in",
            imageUrl = unsplash("photo-1737517302831-e7b8a8eaa97c")
        )
    )

    // -------------------------------------------------------------------------
    // Restaurant menus
    // -------------------------------------------------------------------------

    val menuItems = listOf(

        // The Kampung Cafe

        MenuItemEntity(
            "menu-kc-1",
            "restaurant-kampung-cafe",
            "Nasi Goreng",
            8.5,
            "Main"
        ),

        MenuItemEntity(
            "menu-kc-2",
            "restaurant-kampung-cafe",
            "Satay Ayam",
            6.0,
            "Main"
        ),

        MenuItemEntity(
            "menu-kc-3",
            "restaurant-kampung-cafe",
            "Gado-Gado",
            5.5,
            "Salad"
        ),

        MenuItemEntity(
            "menu-kc-4",
            "restaurant-kampung-cafe",
            "Es Kelapa Muda",
            3.0,
            "Drink"
        ),

        // Warung Bambu

        MenuItemEntity(
            "menu-wb-1",
            "restaurant-warung-bambu",
            "Ayam Betutu",
            7.0,
            "Main"
        ),

        MenuItemEntity(
            "menu-wb-2",
            "restaurant-warung-bambu",
            "Tempe Manis",
            4.0,
            "Side"
        ),

        MenuItemEntity(
            "menu-wb-3",
            "restaurant-warung-bambu",
            "Sayur Urap",
            4.5,
            "Salad"
        ),

        MenuItemEntity(
            "menu-wb-4",
            "restaurant-warung-bambu",
            "Es Jeruk",
            2.5,
            "Drink"
        ),

        // Seminyak Garden Kitchen

        MenuItemEntity(
            "menu-sgk-1",
            "restaurant-seminyak-garden",
            "Ikan Bakar",
            9.0,
            "Main"
        ),

        MenuItemEntity(
            "menu-sgk-2",
            "restaurant-seminyak-garden",
            "Nasi Campur",
            7.5,
            "Main"
        ),

        MenuItemEntity(
            "menu-sgk-3",
            "restaurant-seminyak-garden",
            "Tahu Goreng",
            3.5,
            "Side"
        ),

        MenuItemEntity(
            "menu-sgk-4",
            "restaurant-seminyak-garden",
            "Es Teh Tarik",
            2.0,
            "Drink"
        ),

        // Ubud Green Kitchen

        MenuItemEntity(
            "menu-ugk-1",
            "restaurant-ubud-green",
            "Nasi Goreng Sayur",
            7.5,
            "Main"
        ),

        MenuItemEntity(
            "menu-ugk-2",
            "restaurant-ubud-green",
            "Gado-Gado Ubud",
            6.0,
            "Salad"
        ),

        MenuItemEntity(
            "menu-ugk-3",
            "restaurant-ubud-green",
            "Tempeh Sambal Bowl",
            7.0,
            "Main"
        ),

        MenuItemEntity(
            "menu-ugk-4",
            "restaurant-ubud-green",
            "Tropical Fruit Bowl",
            5.5,
            "Dessert"
        ),

        // Canggu Harvest Table

        MenuItemEntity(
            "menu-cht-1",
            "restaurant-canggu-harvest",
            "Vegetable Nasi Goreng",
            7.0,
            "Main"
        ),

        MenuItemEntity(
            "menu-cht-2",
            "restaurant-canggu-harvest",
            "Coconut Vegetable Curry",
            8.5,
            "Main"
        ),

        MenuItemEntity(
            "menu-cht-3",
            "restaurant-canggu-harvest",
            "Tofu & Tempeh Satay",
            7.5,
            "Main"
        ),

        MenuItemEntity(
            "menu-cht-4",
            "restaurant-canggu-harvest",
            "Balinese Vegetable Rice Bowl",
            8.0,
            "Main"
        )
    )
}