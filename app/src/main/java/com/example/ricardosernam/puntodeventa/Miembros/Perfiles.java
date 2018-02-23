package com.example.ricardosernam.puntodeventa.Miembros;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toolbar;

import com.example.ricardosernam.puntodeventa.R;


public class Perfiles extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfiles, container, false);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getFragmentManager());
        tabLayout =  getActivity().findViewById(R.id.TLtabla);
        viewPager =  view.findViewById(R.id.VPpager);

        tabLayout.setVisibility(View.VISIBLE);

        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            switch(i) {
                case 0:
                    fragment = new MiPerfil();
                    break;
                case 1:
                    fragment = new Miembros();
                    break;
                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mi Perfil";
                case 1:
                    return "Miembros";
            }
            return null;
        }

    }
}
