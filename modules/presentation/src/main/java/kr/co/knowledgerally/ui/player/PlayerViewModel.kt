package kr.co.knowledgerally.ui.player

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kr.co.knowledgerally.base.BaseViewModel
import kr.co.knowledgerally.domain.usecase.GetPlayerLectureBundleUseCase
import kr.co.knowledgerally.ui.model.PlayerLectureModel
import kr.co.knowledgerally.ui.model.toPlayerPresentation
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getPlayerLectureBundleUseCase: GetPlayerLectureBundleUseCase
) : BaseViewModel() {

    private val _tabState = MutableStateFlow(PlayerTabState.DEFAULT)
    val tabState: StateFlow<PlayerTabState> = _tabState.asStateFlow()

    private val _uiState = MutableStateFlow<PlayerUiState>(PlayerUiState.Loading)
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        fetchCoachLectures()
    }

    private fun fetchCoachLectures() {
        _uiState.value = PlayerUiState.Loading
        launch {
            val result = getPlayerLectureBundleUseCase()
            result
                .onSuccess { lectures ->
                    _uiState.value = PlayerUiState.Success(
                        matchingLectures = lectures.onboardingLectures.map { it.toPlayerPresentation() as PlayerLectureModel.Matching },
                        scheduledLectures = lectures.ongoingLectures.map { it.toPlayerPresentation() as PlayerLectureModel.Scheduled },
                        completedLectures = lectures.doneLectures.map { it.toPlayerPresentation() as PlayerLectureModel.Completed },
                    )
                }
                .onFailure { _uiState.value = PlayerUiState.Failure }
        }
    }

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.selectedTab.ordinal) {
            _tabState.update { it.copy(selectedTab = PlayerTabState.Tab.values()[newIndex]) }
        }
    }
}
