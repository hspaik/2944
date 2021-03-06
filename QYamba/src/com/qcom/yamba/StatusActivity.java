package com.qcom.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;

public class StatusActivity extends Activity {
	private int defaultTextColor;
	private Button buttonUpdate;
	private EditText textStatus;
	private TextView textCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		buttonUpdate = (Button) findViewById(R.id.button_update);
		textStatus = (EditText) findViewById(R.id.text_status);
		textCount = (TextView) findViewById(R.id.text_count);
		defaultTextColor = textCount.getTextColors().getDefaultColor();

		buttonUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				final String status = textStatus.getText().toString();

				new UpdateStatusTask().execute(status);
				
				Log.d("Yamba", "button clicked: " + status);
			}

		});


		textStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - s.length();
				textCount.setText(Integer.toString(count));

				if (count < 50) {
					textCount.setTextColor(Color.RED);
				} else {
					textCount.setTextColor(defaultTextColor);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});
	}


	class UpdateStatusTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			YambaClient client = new YambaClient("student", "password");
			client.updateStatus(params[0]);
			return "Successfully posted";
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(StatusActivity.this, result,
					Toast.LENGTH_LONG).show();
		}

	};
}
