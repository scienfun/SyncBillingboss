package billingboss.sync;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.database.Cursor;

import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class sync extends Activity {
    /** Called when the activity is first created. */
    Cursor C;
    String server="http://10.152.19.198:3000";
    public void onCreate(Bundle savedInstanceState) 
    	   {
        super.onCreate(savedInstanceState);
        C = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        startManagingCursor(C);   
        C.moveToFirst();
        setContentView(R.layout.main);
        final Button button = (Button)findViewById(R.id.Button01);
        final Button button2 = (Button)findViewById(R.id.Button02);
        final Button button3 = (Button)findViewById(R.id.Button03);
        final EditText edit1 = (EditText)findViewById(R.id.EditText01);
        final EditText edit2 = (EditText)findViewById(R.id.EditText02);
        
        button.setOnClickListener(new Button.OnClickListener()
			        {
						public void onClick(View v) 
						{
							 // login
							HttpPost httppost = new HttpPost(server+"/session/create");
							HttpEntity entity = null; 
					   HttpClient httpclient = new DefaultHttpClient();  
						  List <NameValuePair> nvps = new ArrayList <NameValuePair>();
						  String str1 = edit1.getText().toString();
						  String str2 = edit2.getText().toString();
			      nvps.add(new BasicNameValuePair("login", "a@a.com"));
			      nvps.add(new BasicNameValuePair("password", "1234"));
			      nvps.add(new BasicNameValuePair("android", "true"));
			      try 
			      			{
								httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
								HttpResponse response = httpclient.execute(httppost);
								entity = response.getEntity();
								ByteArrayOutputStream ostream = new ByteArrayOutputStream();  
								response.getEntity().writeTo(ostream);
								Log.e("Request", httppost.getEntity().toString());
								Log.e("Response", ostream.toString());  
							} 
			      catch (UnsupportedEncodingException e) 
			      			{
								e.printStackTrace();
							}
							catch (ClientProtocolException e) 
							{
							  e.printStackTrace();
							} 
							catch (IOException e) 
							{
							  e.printStackTrace();
							}
			      
			   			     
			      if (entity != null) 
			      			{
			    	  	try 
			    	  			{
			    	  		entity.consumeContent();
							    }
			    	  	catch (IOException e) 
			    	  	        {
								  e.printStackTrace();
							     }
			               } 
						}
			        });
   
        button2.setOnClickListener(new Button.OnClickListener()
			        {
						public void onClick(View v) 
						{
							//upload
//							name, address1, address2, city, country, province, postal, phone, fax, website,
//							firstname, lastname,email,phone
	          String name = C.getString(C.getColumnIndexOrThrow(People.NAME));
	          Log.e("Contacts", name);
	          if(C.isLast())
	        	  C.moveToFirst();
	          else
	        	  C.moveToNext();
						}
			        });
   
        button3.setOnClickListener(new Button.OnClickListener()
			        {
						public void onClick(View v) 
						{
							// download
							HttpPost httppost = new HttpPost(server+"/customers/send_customers");
							HttpEntity entity = null; 
					   HttpClient httpclient = new DefaultHttpClient();  
						  List <NameValuePair> nvps = new ArrayList <NameValuePair>();
						  String str1 = edit1.getText().toString();
						  String str2 = edit2.getText().toString();
			      nvps.add(new BasicNameValuePair("login", "a@a.com"));
			      nvps.add(new BasicNameValuePair("password", "1234"));
			      try 
			      			{
								httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
								HttpResponse response = httpclient.execute(httppost);
								entity = response.getEntity();
								ByteArrayOutputStream ostream = new ByteArrayOutputStream();  
								String customer;
								entity.writeTo(ostream);
								if(httpclient.getParams()==null);
								Log.e("HTTP ERROR","getparams error");
//							 if((customer = )
								//if(ostream.toString().contains("andrew"))
								 Log.e("HTTP PARAMS", ostream.toString());
							// Log.e("HTTP PARAMS",entity.getContent().toString());
							 
							 
							 
								if (entity != null)
								entity.consumeContent();
							} 
			      catch (UnsupportedEncodingException e) 
			      			{
								e.printStackTrace();
							}
							catch (ClientProtocolException e) 
							{
							  e.printStackTrace();
							} 
							catch (IOException e) 
							{
							  e.printStackTrace();
							}
						}
			        });
    		}
}

