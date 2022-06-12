package kr.co.knowledgerally.ui.main.navigation

import androidx.annotation.StringRes
import kr.co.knowledgerally.ui.R
import kr.co.knowledgerally.ui.coach.navigation.CoachDestination
import kr.co.knowledgerally.ui.home.navigation.HomeDestination
import kr.co.knowledgerally.ui.player.navigation.PlayerDestination
import kr.co.knowledgerally.ui.mypage.navigation.MyPageDestination

private const val NO_ROUTE = "no_route"

enum class MainDestination(
    val route: String,
    val activeIconResId: Int,
    val inactiveIconResId: Int,
    @StringRes val iconTextResId: Int,
) {
    Home(
        route = HomeDestination.route,
        activeIconResId = R.drawable.ic_home_active,
        inactiveIconResId = R.drawable.ic_home_inactive,
        iconTextResId = R.string.nav_home,
    ),
    Player(
        route = PlayerDestination.route,
        activeIconResId = R.drawable.ic_player_active,
        inactiveIconResId = R.drawable.ic_player_inactive,
        iconTextResId = R.string.nav_player,
    ),
    Register(
        route = NO_ROUTE,
        activeIconResId = R.drawable.ic_addclass_inactive,
        inactiveIconResId = R.drawable.ic_addclass_inactive,
        iconTextResId = R.string.nav_register
    ),
    Coach(
        route = CoachDestination.route,
        activeIconResId = R.drawable.ic_coach_active,
        inactiveIconResId = R.drawable.ic_coach_inactive,
        iconTextResId = R.string.nav_coach,
    ),
    MyPage(
        route = MyPageDestination.route,
        activeIconResId = R.drawable.ic_mypage_active,
        inactiveIconResId = R.drawable.ic_mypage_inactive,
        iconTextResId = R.string.nav_mypage,
    );
}
