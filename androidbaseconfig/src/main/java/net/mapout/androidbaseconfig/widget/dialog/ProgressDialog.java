package net.mapout.androidbaseconfig.widget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import net.mapout.androidbaseconfig.R;
import net.mapout.androidbaseconfig.util.GenericTools;


public class ProgressDialog extends BaseDialog {

    private TextView mTvMessage;
    private String mMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.view_loading, container, false);
        return mContentView;
    }


    @Override
    protected void getExtraParams() {

        Bundle bundle = getArguments();
        if (bundle == null)
            return;
        mMessage = bundle.getString("message");

    }

    public void setMessage(String text) {
        Bundle bundle = buildArguments();
        bundle.putString("message", text);
        setArguments(bundle);
    }

    @Override
    protected void initComponents(View view) {
        mTvMessage = (TextView) view.findViewById(R.id.define_porgress_tvMessage);
        if (!TextUtils.isEmpty(mMessage)) {
            mTvMessage.setText(mMessage);
        }

    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        WindowManager.LayoutParams params = this.getDialog().getWindow().getAttributes();
        params.gravity = Gravity.TOP;
        params.y = GenericTools.dip2px(getActivity(), 50);
        this.getDialog().getWindow().setAttributes(params);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }
}
