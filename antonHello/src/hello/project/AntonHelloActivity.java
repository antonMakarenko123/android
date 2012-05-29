package hello.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.droidsoapclient.client.SoapClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AntonHelloActivity extends ListActivity {
	private static final String SOAP_ACTION_GET = "http://sho/getItems";
	private static final String SOAP_ACTION_BUY = "http://sho/buyItem";
	private static final String ADDRESS = "http://10.160.56.105:8080/sho/services/ItemService";
	private static final String NAMESPACE = "http://sho/";
	private static final String METHOD_BUY = "buyItem";
	private static final String METHOD_ALL = "getItems";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initList();
	}

	private void initList() {

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_ALL);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(ADDRESS
					+ "?wsdl");
			androidHttpTransport.call(SOAP_ACTION_GET, envelope);
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
			String response = resultsRequestSOAP.getProperty(0).toString();
			
		//	 String[] productList ={"1,asef","2,qwerwe"};
			String[] productList = Deserializator.deserialize(response);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.rowlayout, R.id.label, productList);
			setListAdapter(adapter);

		} catch (Exception e) {
			e.printStackTrace();
			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					AntonHelloActivity.this);
			dialogo.setTitle("title");
			dialogo.setMessage(e.getMessage());
			dialogo.setNeutralButton("OK", null);
			dialogo.show();
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		String[] fields = item.split(",");
		SoapObject request = new SoapObject(NAMESPACE, METHOD_BUY);
		PropertyInfo propInfo = new PropertyInfo();
		propInfo.name = "id";
		propInfo.type = PropertyInfo.INTEGER_CLASS;
		request.addProperty(propInfo, Integer.parseInt(fields[0]));
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ADDRESS
				+ "?wsdl");
		try {
			androidHttpTransport.call(SOAP_ACTION_BUY, envelope);
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
			String[] field = item.split(",");
			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					AntonHelloActivity.this);
			dialogo.setTitle("title");
			dialogo.setMessage(resultsRequestSOAP.getProperty(0).toString());
			dialogo.setNeutralButton("OK", null);
			dialogo.show();

		} catch (Exception e) {
			e.printStackTrace();
			AlertDialog.Builder dialogo = new AlertDialog.Builder(
					AntonHelloActivity.this);
			dialogo.setTitle("title");
			dialogo.setMessage( e.getMessage());
			dialogo.setNeutralButton("OK", null);
			dialogo.show();
		}
		initList();

	}
}
