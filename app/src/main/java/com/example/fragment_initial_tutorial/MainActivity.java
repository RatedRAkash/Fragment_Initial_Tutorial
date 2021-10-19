package com.example.fragment_initial_tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private Button buttonAddFragment;
    private TextView textViewViewFragmentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FRAGMENT MANAGER initialize korlam
        fragmentManager = getSupportFragmentManager();

        buttonAddFragment = findViewById(R.id.buttonAddFragment);
        textViewViewFragmentCount = findViewById(R.id.textViewFragmentCount);

        textViewViewFragmentCount.setText("Fragment count in Back Stack: "+fragmentManager.getBackStackEntryCount());
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                textViewViewFragmentCount.setText("Fragment count in Back Stack: "+fragmentManager.getBackStackEntryCount());
            }
        });

        buttonAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmentComplex();
            }
        });

    }

    private void addFragment(){

        //ekbar COMMIT kore gele shei "FRAGMENT TRANSACTION" er instance sesh... then amader abar INITIALIZE korte hobe "FRAGMENT TRANSACTION"
        fragmentTransaction = fragmentManager.beginTransaction();

        //Sample Fragment er Object create kore shei Object ke amar Activity er moddhe dukhai dilam ei code er madhhome
        SampleFragment sampleFragment = new SampleFragment();
        fragmentTransaction.add(R.id.fragmentContainer_in_main, sampleFragment); //Mane ei MainActivity er XML er vitorer CONTAINER er moddhe FRAGMENT dukailam
        fragmentTransaction.commit();
    }

    private void addFragmentComplex(){

        Fragment fragment;

        //***********With Back Stack Method*****************
//        switch (fragmentManager.getBackStackEntryCount()){
//            case 0: fragment = new SampleFragment();
//                    break;
//            case 1: fragment = new SampleFragment2();
//                break;
//            case 2: fragment = new SampleFragment3();
//                break;
//
//            default: fragment = new SampleFragment();
//                break;
//        }
        //***********With Back Stack Method End*****************

        fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_in_main);

        if(fragment instanceof SampleFragment){
            fragment = new SampleFragment2();
        }
        else if(fragment instanceof SampleFragment2){
            fragment = new SampleFragment3();
        }
        else if(fragment instanceof SampleFragment3){
            fragment = new SampleFragment();
        }
        else{
            fragment = new SampleFragment();
        }


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer_in_main, fragment, "DemoFragment"); //Mane ei MainActivity er XML er vitorer CONTAINER er moddhe FRAGMENT dukailam
        //fragmentTransaction.addToBackStack(null);// Back Button Click korle eita stack er TOP Fragment destroy hobe
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_in_main);
        if(fragment!=null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

        else{
            super.onBackPressed();
        }

    }


}