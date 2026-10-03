package com.example.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class RewardedAdStatus {
  IDLE,
  LOADING,
  LOADED,
  FAILED_TO_LOAD,
  SHOWN,
  DISMISSED,
  REWARD_EARNED
}

class RewardedAdManager(private val context: Context) {

  companion object {
    private const val TAG = "RewardedAdManager"
    const val TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
  }

  private var rewardedAd: RewardedAd? = null
  private var isAdCompleted = false

  private val _adStatus = MutableStateFlow(RewardedAdStatus.IDLE)
  val adStatus: StateFlow<RewardedAdStatus> = _adStatus.asStateFlow()

  private val _statusMessage = MutableStateFlow<String?>(null)
  val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

  fun initialize(onInitialized: (() -> Unit)? = null) {
    try {
      MobileAds.initialize(context) { status ->
        Log.d(TAG, "Google MobileAds initialized: $status")
        loadRewardedAd()
        onInitialized?.invoke()
      }
    } catch (e: Exception) {
      Log.e(TAG, "Error initializing MobileAds", e)
      _adStatus.value = RewardedAdStatus.FAILED_TO_LOAD
      _statusMessage.value = "AdMob init error: ${e.message}"
    }
  }

  fun loadRewardedAd() {
    if (_adStatus.value == RewardedAdStatus.LOADING) return
    _adStatus.value = RewardedAdStatus.LOADING
    _statusMessage.value = "Loading rewarded ad..."
    Log.d(TAG, "Loading rewarded ad with unit ID: $TEST_AD_UNIT_ID")

    try {
      val adRequest = AdRequest.Builder().build()
      RewardedAd.load(
        context,
        TEST_AD_UNIT_ID,
        adRequest,
        object : RewardedAdLoadCallback() {
          override fun onAdLoaded(ad: RewardedAd) {
            Log.d(TAG, "Rewarded ad loaded successfully.")
            rewardedAd = ad
            _adStatus.value = RewardedAdStatus.LOADED
            _statusMessage.value = "Ad ready to watch!"
            setupCallbacks(ad)
          }

          override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            Log.e(TAG, "Rewarded ad failed to load: ${loadAdError.message}")
            rewardedAd = null
            _adStatus.value = RewardedAdStatus.FAILED_TO_LOAD
            _statusMessage.value = "Ad failed to load: ${loadAdError.message}"
          }
        }
      )
    } catch (e: Exception) {
      Log.e(TAG, "Exception loading rewarded ad", e)
      rewardedAd = null
      _adStatus.value = RewardedAdStatus.FAILED_TO_LOAD
      _statusMessage.value = "Failed to load: ${e.message}"
    }
  }

  private fun setupCallbacks(ad: RewardedAd) {
    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
      override fun onAdShowedFullScreenContent() {
        Log.d(TAG, "Rewarded ad showed full screen content.")
        _adStatus.value = RewardedAdStatus.SHOWN
        _statusMessage.value = "Ad is showing..."
        isAdCompleted = false
      }

      override fun onAdDismissedFullScreenContent() {
        Log.d(TAG, "Rewarded ad dismissed.")
        _adStatus.value = RewardedAdStatus.DISMISSED
        rewardedAd = null
        // Pre-load next ad for future voluntary views
        loadRewardedAd()
      }

      override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        Log.e(TAG, "Rewarded ad failed to show: ${adError.message}")
        rewardedAd = null
        _adStatus.value = RewardedAdStatus.FAILED_TO_LOAD
        _statusMessage.value = "Ad failed to show: ${adError.message}"
        loadRewardedAd()
      }
    }
  }

  fun showRewardedAd(
    activity: Activity,
    onRewardEarned: (rewardAmount: Int, rewardType: String) -> Unit,
    onAdUnavailable: (() -> Unit)? = null
  ) {
    val ad = rewardedAd
    if (ad != null && _adStatus.value == RewardedAdStatus.LOADED) {
      isAdCompleted = false
      ad.show(activity) { rewardItem ->
        Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
        isAdCompleted = true
        _adStatus.value = RewardedAdStatus.REWARD_EARNED
        _statusMessage.value = "Reward earned!"
        onRewardEarned(rewardItem.amount, rewardItem.type)
      }
    } else {
      Log.w(TAG, "Rewarded ad not ready yet. Status: ${_adStatus.value}")
      onAdUnavailable?.invoke()
      loadRewardedAd()
    }
  }

  fun isAdLoaded(): Boolean {
    return rewardedAd != null && _adStatus.value == RewardedAdStatus.LOADED
  }
}

fun android.content.Context.findActivity(): android.app.Activity? {
  var currentContext = this
  while (currentContext is android.content.ContextWrapper) {
    if (currentContext is android.app.Activity) return currentContext
    currentContext = currentContext.baseContext
  }
  return null
}

