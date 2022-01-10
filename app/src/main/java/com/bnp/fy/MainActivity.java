package com.bnp.fy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity{

    Button btnSendMessage;
    EditText message,mobileNumber;
    CountryCodePicker countryCodePicker;
    String strMessage,strMobileNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        btnSendMessage=findViewById(R.id.btnsend);
        message=findViewById(R.id.message);
        mobileNumber=findViewById(R.id.mobileNumber);
        countryCodePicker=findViewById(R.id.countryCodePicker);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strMessage=message.getText().toString();
                strMobileNumber=mobileNumber.getText().toString();

                if(mobileNumber.getText().toString().isEmpty() && message.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"enter mobile number and message",Toast.LENGTH_SHORT).show();
                }else{
                    countryCodePicker.registerCarrierNumberEditText(mobileNumber);
                    strMobileNumber=countryCodePicker.getFullNumber();

                    boolean installed = appInstalledOrNot();
                    if(installed){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+strMobileNumber+"&text="+strMessage));
                        startActivity(intent);
                        mobileNumber.setText("");
                        message.setText("");
                    }else{
                        Toast.makeText(MainActivity.this,"whatsapp not install",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private boolean appInstalledOrNot(){
        PackageManager packageManager=getPackageManager();
        boolean appInstalled;
        try{
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            appInstalled=true;
        }catch (PackageManager.NameNotFoundException e){
            appInstalled = false;
        }
        return appInstalled;
    }
}