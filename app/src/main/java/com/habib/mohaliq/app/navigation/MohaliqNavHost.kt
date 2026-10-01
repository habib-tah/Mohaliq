package com.habib.mohaliq.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.habib.mohaliq.core.model.MapLocation
import com.habib.mohaliq.core.model.PlaceType
import com.habib.mohaliq.feature.ai.AiChatRoute
import com.habib.mohaliq.feature.auth.LoginRoute
import com.habib.mohaliq.feature.auth.RegisterRoute
import com.habib.mohaliq.feature.destination.DestinationRoute
import com.habib.mohaliq.feature.explore.ExploreRoute
import com.habib.mohaliq.feature.favorites.FavoritesRoute
import com.habib.mohaliq.feature.history.HistoryRoute
import com.habib.mohaliq.feature.home.HomeRoute
import com.habib.mohaliq.feature.hotel.HotelRoute
import com.habib.mohaliq.feature.hotel.booking.BookingRoute
import com.habib.mohaliq.feature.map.MapRoute
import com.habib.mohaliq.feature.payment.PaymentRoute
import com.habib.mohaliq.feature.profile.ProfileRoute
import com.habib.mohaliq.feature.restaurant.RestaurantRoute
import com.habib.mohaliq.feature.search.SearchRoute
import com.habib.mohaliq.feature.splash.SplashRoute

private fun navigateToPlace(
    navController: NavHostController,
    id: String,
    type: PlaceType
) {
    when (type) {
        PlaceType.HOTEL -> navController.navigate(Destinations.Hotel.createRoute(id))
        PlaceType.RESTAURANT -> navController.navigate(Destinations.Restaurant.createRoute(id))
        PlaceType.DESTINATION -> navController.navigate(Destinations.Destination.createRoute(id))
    }
}

private fun navigateToMap(
    navController: NavHostController,
    location: MapLocation
) {
    navController.navigate(
        Destinations.Map.createRoute(location.latitude, location.longitude, location.title)
    )
}

@Composable
fun MohaliqNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Splash.route
    ) {

        composable(Destinations.Splash.route) {

            SplashRoute(

                onNavigateHome = {

                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.Splash.route) { inclusive = true }
                    }

                },

                onNavigateLogin = {

                    navController.navigate(Destinations.Login.route) {
                        popUpTo(Destinations.Splash.route) { inclusive = true }
                    }

                }

            )

        }

        composable(Destinations.Login.route) {

            LoginRoute(

                onNavigateHome = {

                    navController.navigate(Destinations.Home.route) {
                        popUpTo(0)
                    }

                },

                onNavigateToRegister = {

                    navController.navigate(Destinations.Register.route)

                }

            )

        }

        composable(Destinations.Register.route) {

            RegisterRoute(

                onNavigateHome = {

                    navController.navigate(Destinations.Home.route) {
                        popUpTo(0)
                    }

                },

                onNavigateToLogin = {

                    navController.navigate(Destinations.Login.route)

                }

            )

        }

        composable(Destinations.Home.route) {

            HomeRoute(

                viewModel = hiltViewModel(),

                onNavigateToDetail = { id ->

                    navigateToPlace(navController, id, PlaceType.HOTEL)

                },

                onNavigateToCategory = { categoryId ->

                    when (categoryId) {

                        "hotel", "popular", "recommendation" ->
                            navController.navigate(Destinations.Search.createRoute("HOTEL"))

                        "restaurant" -> navController.navigate(Destinations.Search.createRoute("RESTAURANT"))

                        "tour", "guide" -> navController.navigate(Destinations.Search.createRoute("DESTINATION"))

                        else -> navController.navigate(Destinations.Explore.route)

                    }

                },

                onNavigateToSearch = {

                    navController.navigate(Destinations.Search.createRoute())

                },

                onNavigateToAiChat = {

                    navController.navigate(Destinations.AiChat.route)

                },

                onBottomNavigation = { destination ->

                    navController.navigateSingleTop(destination.route)

                }

            )

        }

        composable(Destinations.Explore.route) {

            ExploreRoute(

                viewModel = hiltViewModel(),

                onNavigateToDetail = { id, type ->

                    navigateToPlace(navController, id, type)

                },

                onNavigateToSection = { section ->

                    when (section) {

                        "popular" -> navController.navigate(Destinations.Search.createRoute("DESTINATION"))

                        "frequently" -> navController.navigate(Destinations.Search.createRoute("RESTAURANT"))

                        "nearest" -> navController.navigate(Destinations.Search.createRoute("HOTEL"))

                        else -> navController.navigate(Destinations.Search.createRoute())

                    }

                },

                onBottomNavigation = { destination ->

                    navController.navigateSingleTop(destination.route)

                }

            )

        }

        composable(
            route = Destinations.Search.route,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {

            SearchRoute(

                onBack = {
                    navController.popBackStack()
                },

                onNavigateToDetail = { id, type ->

                    navigateToPlace(navController, id, type)

                }

            )

        }

        composable(Destinations.AiChat.route) {

            AiChatRoute(

                onBack = {
                    navController.popBackStack()
                }

            )

        }

        composable(
            route = Destinations.Destination.route,
            arguments = listOf(navArgument("destinationId") { type = NavType.StringType })
        ) {

            DestinationRoute(

                onBack = {
                    navController.popBackStack()
                },

                onOpenMap = { location ->

                    navigateToMap(navController, location)

                },

                onNavigateToDetail = { id ->

                    navigateToPlace(navController, id, PlaceType.DESTINATION)

                },

                onNavigateToSearch = {

                    navController.navigate(Destinations.Search.createRoute("DESTINATION"))

                }

            )

        }

        composable(
            route = Destinations.Hotel.route,
            arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
        ) {

            HotelRoute(

                onNavigateBack = {

                    navController.popBackStack()

                },

                onNavigateBooking = { hotelId ->

                    navController.navigate(
                        Destinations.HotelBooking.createRoute(hotelId)
                    )

                },

                onOpenMap = { location ->

                    navigateToMap(navController, location)

                }

            )

        }

        composable(
            route = Destinations.HotelBooking.route,
            arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
        ) {

            BookingRoute(

                onBack = {
                    navController.popBackStack()
                },

                onOpenMap = { location ->

                    navigateToMap(navController, location)

                },

                onNavigateToPayment = { hotelId, roomId, checkIn, checkOut, guestCount ->

                    navController.navigate(
                        Destinations.Payment.createRoute(
                            placeType = "HOTEL",
                            placeId = hotelId,
                            secondaryId = roomId,
                            dateRange = "$checkIn - $checkOut",
                            guestCount = guestCount
                        )
                    )
                }

            )

        }

        composable(
            route = Destinations.Payment.route,
            arguments = listOf(
                navArgument("placeType") {
                    type = NavType.StringType
                },
                navArgument("placeId") {
                    type = NavType.StringType
                },
                navArgument("secondaryId") {
                    type = NavType.StringType
                },
                navArgument("dateRange") {
                    type = NavType.StringType
                },
                navArgument("guestCount") {
                    type = NavType.IntType
                }
            )
        ) {
            PaymentRoute(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateHome = {
                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Destinations.Restaurant.route,
            arguments = listOf(
                navArgument("restaurantId") {
                    type = NavType.StringType
                }
            )
        ) {
            RestaurantRoute(
                onBack = {
                    navController.popBackStack()
                },
                onOpenMap = { location ->
                    navigateToMap(navController, location)
                },
                onNavigateToPayment = { restaurantId, reservationDate, partySize ->

                    navController.navigate(
                        Destinations.Payment.createRoute(
                            placeType = "RESTAURANT",
                            placeId = restaurantId,
                            secondaryId = "-",
                            dateRange = reservationDate,
                            guestCount = partySize
                        )
                    )
                }
            )
        }

        composable(
            route = Destinations.Map.route,
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType },
                navArgument("longitude") { type = NavType.FloatType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val args = backStackEntry.arguments

            MapRoute(
                latitude = (args?.getFloat("latitude") ?: 0f).toDouble(),
                longitude = (args?.getFloat("longitude") ?: 0f).toDouble(),
                title = args?.getString("title") ?: "",
                onBack = {
                    navController.popBackStack()
                }
            )

        }

        composable(Destinations.History.route) {

            HistoryRoute(

                onNavigateToDetail = { id, type ->

                    navigateToPlace(navController, id, type)

                },

                onBottomNavigation = { destination ->

                    navController.navigateSingleTop(destination.route)

                }

            )

        }

        composable(Destinations.Favorites.route) {

            FavoritesRoute(

                onNavigateToDetail = { id, type ->

                    navigateToPlace(navController, id, type)

                },

                onBottomNavigation = { destination ->

                    navController.navigateSingleTop(destination.route)

                }

            )

        }

        composable(Destinations.Profile.route) {

            ProfileRoute(

                onNavigateToLogin = {

                    navController.navigate(Destinations.Login.route) {
                        popUpTo(0)
                    }

                },

                onBottomNavigation = { destination ->

                    navController.navigateSingleTop(destination.route)

                }

            )

        }

    }

}
