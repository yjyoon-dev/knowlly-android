package kr.co.knowledgerally.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kr.co.knowledgerally.ui.component.KnowllyTopAppBar
import kr.co.knowledgerally.ui.component.KnowllyTopAppBarBall
import kr.co.knowledgerally.ui.component.KnowllyTopAppBarLogo
import kr.co.knowledgerally.ui.component.KnowllyTopAppBarNotification
import kr.co.knowledgerally.ui.component.NavigationType
import kr.co.knowledgerally.ui.main.navigation.MainDestination
import kr.co.knowledgerally.ui.main.navigation.MainNavHost
import kr.co.knowledgerally.ui.main.navigation.rememberMainNavigation
import kr.co.knowledgerally.ui.theme.KnowllyTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigateToRegister: () -> Unit,
    navigateToBall: () -> Unit,
    navigateToNotification: () -> Unit,
) {
    val navController = rememberNavController()
    val navigation = rememberMainNavigation(navController, navigateToRegister)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showWelcome by viewModel.showWelcome.collectAsState()

    Scaffold(
        topBar = {
            KnowllyTopAppBar(
                navigationType = NavigationType.None,
                actions = {
                    if (currentDestination?.route == MainDestination.Home.route) {
                        KnowllyTopAppBarLogo(Modifier.padding(start = 24.dp))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    KnowllyTopAppBarBall(ballCount = 10, onClick = navigateToBall)
                    KnowllyTopAppBarNotification(onClick = navigateToNotification)
                }
            )
        },
        modifier = Modifier,
        bottomBar = {
            MainNavigationBar(
                currentDestination = currentDestination,
                onNavigate = { navigation.navigateTo(it) },
            )
        },
    ) { padding ->
        MainNavHost(
            navigation = navigation,
            modifier = Modifier.padding(padding),
        )
    }

    if (showWelcome) {
        WelcomeDialog(onDismiss = { viewModel.shownWelcome() })
    }
}

@Composable
private fun MainNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (MainDestination) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = KnowllyTheme.colors.grayEF
        )
        Row(
            modifier = Modifier
                .height(NavigationBarHeight)
                .background(KnowllyTheme.colors.grayFF)
        ) {

            MainDestination.values().forEach { destination ->
                val selected = destination in currentDestination

                NavigationBarItem(
                    selected = selected,
                    destination = destination,
                    onClick = { onNavigate(destination) }
                )
            }
        }
    }
}

private operator fun NavDestination?.contains(destination: MainDestination): Boolean {
    if (this == null) {
        return false
    }
    return hierarchy.any { it.route == destination.route }
}

@Composable
private fun RowScope.NavigationBarItem(
    selected: Boolean,
    destination: MainDestination,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (selected) KnowllyTheme.colors.primaryDark else KnowllyTheme.colors.gray8F
    val iconRes = if (selected) destination.activeIconResId else destination.inactiveIconResId
    Column(
        modifier = modifier
            .fillMaxSize()
            .weight(1f)
            .selectable(
                selected = selected,
                onClick = onClick,
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(id = iconRes), contentDescription = null, tint = color)
        Text(
            text = stringResource(id = destination.iconTextResId),
            style = KnowllyTheme.typography.caption,
            color = color
        )
    }
}

private val NavigationBarHeight: Dp = 56.dp

@Preview(showBackground = true)
@Composable
fun MainNavigationBarPreview() {
    KnowllyTheme {
        MainNavigationBar(
            currentDestination = null,
            onNavigate = { }
        )
    }
}
