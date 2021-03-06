package com.apps.onlinegroceriesworld.utils.BottomSheet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.apps.onlinegroceriesworld.activity.screen.AddressSelectActivity;
import com.apps.onlinegroceriesworld.adapter.AddressListAdapter;
import com.apps.onlinegroceriesworld.api.CommanApiCall.UserProfileCommonApiCall;
import com.apps.onlinegroceriesworld.databinding.BottomAddressListBinding;
import com.apps.onlinegroceriesworld.models.UserAddressList;
import com.apps.onlinegroceriesworld.models.UserModel;
import com.apps.onlinegroceriesworld.utils.LoadingSpinner;
import com.apps.onlinegroceriesworld.utils.UserPrefs;
import com.apps.onlinegroceriesworld.utils.UserResponseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AddressList {
    Context context;
    UserPrefs userPrefs ;
    UserModel userModel ;
    LoadingSpinner loadingSpinner;
    AddressListAdapter addressListAdapter;
    UserResponseHelper helper;
    BottomAddressListBinding binding;
    static GetAddressList getAddressList;
    public AddressList(Context context, LoadingSpinner loadingSpinner , GetAddressList getAddressList) {
        this.context = context;
        this.loadingSpinner = loadingSpinner;
        AddressList.getAddressList = getAddressList;
    }
    public interface GetAddressList{
        void getAddressList(UserAddressList data);
    }
    public void open(){
        userPrefs = new UserPrefs(context);
        userModel = userPrefs.getAuthPreferenceObjectJson(UserPrefs.users);
        helper = new UserResponseHelper(context);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        binding = BottomAddressListBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog.setContentView(binding.getRoot());
        binding.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.showConfirm("Are you sure ?");
                helper.getConfirmOk().setOnClickListener(v1 -> {
                    bottomSheetDialog.dismiss();
                    helper.cancelConfirm();
                });
            }
        });
        binding.addAnother.setOnClickListener(v -> {
            context.startActivity(new Intent(context, AddressSelectActivity.class));
        });
        new UserProfileCommonApiCall(context, new UserProfileCommonApiCall.CallBack() {
            @Override
            public void onGetAddresses(List<UserAddressList> body) {

                addressListAdapter = new AddressListAdapter(context, body, helper, new AddressListAdapter.AddressListInterfaces() {
                    @Override
                    public void getAddressList(UserAddressList data) {
                        userPrefs.setDefaultAddress(data);
                        getAddressList.getAddressList(data);
                        bottomSheetDialog.hide();
                    }
                });


                GridLayoutManager horizontalLayoutManager
                        = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
                binding.address.setLayoutManager(horizontalLayoutManager);
//        binding.exclusiveOffer.setNestedScrollingEnabled(true);
//                binding.address.addItemDecoration( new LayoutMarginDecoration( 2,  Util.convertDpToPx(context, 10)) );
                binding.address.setAdapter(addressListAdapter);
            }
        }).getUserAddressesList(loadingSpinner,userModel);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();
    }
}
