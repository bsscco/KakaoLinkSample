package bsscco.kakaolinksample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

import java.util.Locale;

import rx.android.view.ViewObservable;


public class PagerActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        private KakaoLink mKakaoLink;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

            try {
                mKakaoLink = KakaoLink.getKakaoLink(getActivity());
            } catch (KakaoParameterException e) {
                e.printStackTrace();
            }
            final KakaoTalkLinkMessageBuilder builder = mKakaoLink.createKakaoTalkLinkMessageBuilder();

            final String imgUrl = "https://developers.kakao.com/assets/images/android/a_011.png";
            final String text = "남자들의 여름을 도와줄 반바지 스타일링" +
                    "\n\n남자의 반바지 스타일링. 여름, 너무나도 덥습니다. 긴바지는 보기만 해도 덥습니다. 여름 코디는 청바지에 흰티라는 말은 옛말이 됐지. 이번 여름은 반바지로 시원하게 보내시는 걸..." +
                    "\nhttp://ohou.se";

            ViewObservable.clicks(rootView.findViewById(R.id.appBtn))
                    .subscribe(v -> {
                        try {
                            // 내용은 추가 순서대로 구성됨.
                            builder.addImage(imgUrl, 100, 100);
                            builder.addText(text);
                            builder.addAppButton("오늘의 집 앱에서 보기(appBtn)");
//                                    new AppActionBuilder()
//                                        .addActionInfo(AppActionInfoBuilder
//                                                .createAndroidActionInfoBuilder()
//                                                .setExecuteParam(getString(R.string.kakao_app_key) + "=1111")
////                                                .setMarketParam("referrer=kakaotalklink")
//                                                .build())
//                                        .addActionInfo(AppActionInfoBuilder
//                                                .createiOSActionInfoBuilder()
//                                                .setExecuteParam(getString(R.string.kakao_app_key) + "=1111")
//                                                .build())
//                                            .setUrl("http://ohou.se")
//                                            .build());
//                            builder.addWebButton("오늘의 집 웹에서 보기", "http://ohou.se");

                            String content = builder.build();
                            mKakaoLink.sendMessage(content, getActivity());
                            Log.d("KAKAOLINK", "content : " + content);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                    });
            ViewObservable.clicks(rootView.findViewById(R.id.appLink))
                    .subscribe(v -> {
                        try {
                            // 내용은 추가 순서대로 구성됨.
                            builder.addImage(imgUrl, 100, 100);
                            builder.addText(text);
                            builder.addAppLink("오늘의 집 앱에서 보기(appLink)");
//                                    new AppActionBuilder()
//                                        .addActionInfo(AppActionInfoBuilder
//                                                .createAndroidActionInfoBuilder()
//                                                .setExecuteParam(getString(R.string.kakao_app_key) + "=1111")
////                                                .setMarketParam("referrer=kakaotalklink")
//                                                .build())
//                                        .addActionInfo(AppActionInfoBuilder
//                                                .createiOSActionInfoBuilder()
//                                                .setExecuteParam(getString(R.string.kakao_app_key) + "=1111")
//                                                .build())
//                                            .setUrl("http://ohou.se")
//                                            .build());
//                            builder.addWebButton("오늘의 집 웹에서 보기", "http://ohou.se");

                            String content = builder.build();
                            mKakaoLink.sendMessage(content, getActivity());
                            Log.d("KAKAOLINK", "content : " + content);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                    });
            ViewObservable.clicks(rootView.findViewById(R.id.webBtn))
                    .subscribe(v -> {
                        try {
                            // 내용은 추가 순서대로 구성됨.
                            builder.addImage(imgUrl, 100, 100);
                            builder.addText(text);
                            builder.addWebButton("오늘의 집 웹에서 보기(webBtn)");

                            String content = builder.build();
                            mKakaoLink.sendMessage(content, getActivity());
                            Log.d("KAKAOLINK", "content : " + content);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                    });
            ViewObservable.clicks(rootView.findViewById(R.id.webLink))
                    .subscribe(v -> {
                        try {
                            // 내용은 추가 순서대로 구성됨.
                            builder.addImage(imgUrl, 100, 100);
                            builder.addText(text);
                            builder.addWebLink("오늘의 집 웹에서 보기(webLink)");

                            String content = builder.build();
                            mKakaoLink.sendMessage(content, getActivity());
                            Log.d("KAKAOLINK", "content : " + content);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                    });
            ViewObservable.clicks(rootView.findViewById(R.id.defaultShare))
                    .subscribe(v -> {
                        final String text2 = "남자들의 여름을 도와줄 반바지 스타일링" +
                                "\n\n남자의 반바지 스타일링. 여름, 너무나도 덥습니다. 긴바지는 보기만 해도 덥습니다. 여름 코디는 청바지에 흰티라는 말은 옛말이 됐지. 이번 여름은 반바지로 시원하게 보내시는 걸...";

                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "[내가 꿈꾸는 공간, 오늘의집] " + text2);
                        i.putExtra(Intent.EXTRA_TEXT, "http://ohou.se/cards/21090");
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        getActivity().startActivityForResult(Intent.createChooser(i, "공유하기"), 0);

                    });
            return rootView;
        }
    }
}
