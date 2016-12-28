package com.example.smn.functioncalculator;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class IntroActivity extends AppIntro {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("به محاسبه گر توابع تک متغیره خوش آمدید!", "شما با این برنامه میتوانید مقادیر توابع تک متغیره خود را  در بازه ای مشخص با دقتی خاص محاسبه کرده و نمودار آن را مشاهده کنید", R.drawable.img, 2333));
        addSlide(AppIntroFragment.newInstance("گام اول", "ابتدا میبایست نام و متغیر تابع خود را در کادر بالا مشخص نمایید", R.drawable.name, 2333));
        addSlide(AppIntroFragment.newInstance("گام دوم", "در این بخش میبایست مقادیر کمینه و بیشینه بازه تابع و همچنین دقت گام های آن را وارد کنید", R.drawable.minmax, 2333));
        addSlide(AppIntroFragment.newInstance("گام سوم", "در این بخش میتوانید ثابت های اختیاری تابع خود را ثبت و مقداردهی نمایید و در تابع وارد نمایید", R.drawable.consts, 2333));
        addSlide(AppIntroFragment.newInstance("گام آخر", "در نهایت پس از وارد کردن محتوای تابع بر روی دکمه ستاره کلیک کرده تا نتیجه محاسبات و همچنین نمودار رسم شده را مشاهده نمایید", R.drawable.done, 2333));
        addSlide(AppIntroFragment.newInstance("آماده برای شروع!", "امیدواریم برنامه پیش رو کارایی لازم را داشته باشد و برای ارسال نظرات به صفحه درباره ما مراجعه کنید", R.drawable.shelp, 2333));

        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));   //3F51B5
        setSeparatorColor(Color.parseColor("#2196F3"));     //2196F3

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
