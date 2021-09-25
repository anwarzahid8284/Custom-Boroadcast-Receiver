package com.example.callerannouncer.adsintegration

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.callerannouncer.R
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

fun Context.loadAdmobInterstitialAd() {

}

fun Context.showAdmobInterstitialAd() {

}

fun Context.loadAdmobNativeAd(admobNativeAdUnit: String, nativeAdView: NativeAdView) {
    val builder = AdLoader.Builder(this, admobNativeAdUnit)

}

fun Context.showAdmobNativeAd(parent: ViewGroup, ad: NativeAd) {
    // Inflate a layout and add it to the parent ViewGroup.
    val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val adView = inflater.inflate(R.layout.admob_nativ_ad_view, parent) as NativeAdView

    // Locate the view that will hold the headline, set its text, and use the
    // NativeAdView's headlineView property to register it.
    val headlineView = adView.findViewById<TextView>(R.id.primary)
    val mediaView = adView.findViewById<MediaView>(R.id.media_view)
    val buttonView=adView.findViewById<Button>(R.id.cta)
    val iconView =adView.findViewById<ImageView>(R.id.icon)
    val bodyView=adView.findViewById<TextView>(R.id.body)

    headlineView.text = ad.headline
    adView.headlineView = headlineView
    adView.mediaView = mediaView
    bodyView.text=ad.body
    adView.bodyView=bodyView
    adView.iconView=iconView
    adView.callToActionView=buttonView

    adView.setNativeAd(ad)
    parent.removeAllViews()
    parent.addView(adView)

}

fun Context.loadAdmobBanner() {

}

fun Context.showAdmobBanner() {

}