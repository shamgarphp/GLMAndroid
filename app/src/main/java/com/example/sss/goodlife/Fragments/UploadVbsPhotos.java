package com.example.sss.goodlife.Fragments;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.LocationAdapter;
import com.example.sss.goodlife.Adapters.ProgramsIdAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.CameraUtils;
import com.example.sss.goodlife.ImagePath_Marshmallow;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.Models.FormStatus;
import com.example.sss.goodlife.Models.Locations;
import com.example.sss.goodlife.Models.MultipleImages;
import com.example.sss.goodlife.Models.ParticipantsTypeIds;
import com.example.sss.goodlife.Models.ProgramIds;
import com.example.sss.goodlife.Models.ProgramIdsStatus;
import com.example.sss.goodlife.Models.Status;
import com.example.sss.goodlife.R;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class UploadVbsPhotos extends Fragment {
    private TextView gettingMultipleImagesViews,uploadPicsSelectDate;
    private LinearLayout multipleImagesParentLayout;
    private Spinner uploadpicsSpinner,uploadPicsLocations;
    private ImageView vbs_upload_quotation_image;
    private Button uploadPicsSumitButton;
    private EditText uploadPicsDescription;
    private String finalImage="";

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;


    //Api calls
    private ApiService apiService;
    private List<ProgramIds> programIds;
    private String programId,locationId;
    private ProgramsIdAdapter programidsAdapter;
    private LocationAdapter locationAdapter;
    private ProgressDialog progressDialog,reportSubDialog;

    private List<Locations> locationTypes;
    private String Categories;
    private ArrayAdapter arrayAdapter;

    private ArrayList<String> imagesList=new ArrayList<>();
    private ArrayList<String> categoryList=new ArrayList<>();

    private int GALLERY = 1, CAMERA = 2;
    private boolean permissionCheck=false;
    private Bitmap bmp;
    private String getImageUrl ="";
    private Uri fileUri = null;//Uri to capture image

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 4;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;
    public UploadVbsPhotos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Upload photos");
        View view= inflater.inflate(R.layout.fragment_upload_vbs_photos, container, false);

        if (!permissionCheck){
            requestMultiplePermissions();
        }

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please wait...,");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        gettingMultipleImagesViews=view.findViewById(R.id.gettingMultipleImagesViews);
        multipleImagesParentLayout=view.findViewById(R.id.multipleImagesParentLayout);
        uploadPicsSelectDate=view.findViewById(R.id.uploadPicsSelectDate);
        uploadpicsSpinner=view.findViewById(R.id.uploadpicsSpinner);
        uploadPicsLocations=view.findViewById(R.id.uploadPicsLocations);
        uploadPicsSumitButton=view.findViewById(R.id.uploadPicsSumitButton);
        uploadPicsDescription=view.findViewById(R.id.uploadPicsDescription);




        uploadPicsSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                uploadPicsSelectDate.setText(day + "/" + (month + 1)+ "/" + year);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<ProgramIdsStatus> call=apiService.getProgramids();
        call.enqueue(new Callback<ProgramIdsStatus>() {
            @Override
            public void onResponse(retrofit2.Call<ProgramIdsStatus> call, Response<ProgramIdsStatus> response) {
                if (response.body()==null){
                    Toast.makeText(getActivity(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getMessage().size()!=0) {
                    programIds = response.body().getMessage();
                    uploadpicsSpinner.setPrompt("Select Location");
                    programidsAdapter = new ProgramsIdAdapter(getActivity(), (ArrayList<ProgramIds>) programIds);
                    uploadpicsSpinner.setAdapter(programidsAdapter);
                    progressDialog.dismiss();

                    uploadpicsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            programId= String.valueOf(programidsAdapter.getItem(position).getProgram_id());
                            locationId= String.valueOf(programidsAdapter.getItem(position).getLocation_id());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Programs not forund",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ProgramIdsStatus> call, Throwable t) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        categoryList.add("Select Category");
        categoryList.add("Children");
        categoryList.add("Class time");
        categoryList.add("General session-stage");
        categoryList.add("Parents/Adults");
        categoryList.add("Individual/Children");
        categoryList.add("Food");
        categoryList.add("Prayer/Decision");
        categoryList.add("General");
        categoryList.add("Transport");
        categoryList.add("Testimonials");
        categoryList.add("Others");
        arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,categoryList);
        uploadPicsLocations.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uploadPicsLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categories=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gettingMultipleImagesViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.multiple_images_layout, null);
                // Add the new row before the add field button.

                if (multipleImagesParentLayout.getChildCount()==0){
                    multipleImagesParentLayout.addView(rowView);
                }else {
                    if (bmp==null){
                        Toast.makeText(getActivity(),"please upload image",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    multipleImagesParentLayout.addView(rowView);
                }

                vbs_upload_quotation_image=rowView.findViewById(R.id.mul_image_layout);

                vbs_upload_quotation_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPictureDialog();
                    }
                });

                vbs_upload_quotation_image.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        multipleImagesParentLayout.removeView((View) v.getParent());
                        if (bmp!=null){
                            imagesList.remove(imageToString(bmp));
                        }
                        return true;
                    }
                });
            }

        });


        uploadPicsSumitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                HomeFragment fragment=new HomeFragment();
                transaction.replace(R.id.frameContainer, fragment);
                transaction.commit();
            }
        });
        return view;
    }


    private   void upload(){
        Log.e("Called Upload ()",""+finalImage);
        if (Categories.equals("Select Category")){

            if (bmp!=null){
                imagesList.remove(imageToString(bmp));
            }
            Toast.makeText(getActivity(),"please Select Category",Toast.LENGTH_SHORT).show();
            return;
        }
        if (uploadPicsSelectDate.getText().toString().equals("Select Date")){
            if (bmp!=null){
                imagesList.remove(imageToString(bmp));
            }
            Toast.makeText(getActivity(),"please Select date",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(uploadPicsDescription.getText().toString())){
            if (bmp!=null){
                imagesList.remove(imageToString(bmp));
            }
            uploadPicsDescription.setError("field cant be empty");
            return;
        }
               /* if (bmp==null){
                    Toast.makeText(getActivity(),"please upload image",Toast.LENGTH_SHORT).show();
                    return;
                }*/

            Log.e("images", String.valueOf(imagesList.size()));
            Log.e("Categories", Categories);
            Log.e("programId", programId);
            Log.e("date", uploadPicsSelectDate.getText().toString());

            Log.e("Request Body ",""+programId +", "+
                    finalImage +","+
                    locationId+","+
                    uploadPicsSelectDate.getText().toString()+","+
                    Categories+","+
                    uploadPicsDescription.getText().toString());
            MultipleImages multipleImages=new MultipleImages(imagesList);
            Gson gson = new Gson();
            String Images = gson.toJson(multipleImages);


            Log.e("Images",Images);


            uploadPicsSumitButton.setClickable(false);
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading images");
            progressDialog.setMessage("Please wait...,");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            apiService= APIUrl.getApiClient().create(ApiService.class);
            final retrofit2.Call<FormStatus> call1=apiService.multipleImages(
                    programId,
                    finalImage,
                    locationId,
                    uploadPicsSelectDate.getText().toString(),
                    Categories,
                    uploadPicsDescription.getText().toString()

            );
            call1.enqueue(new Callback<FormStatus>() {
                @Override
                public void onResponse(Call<FormStatus> call, Response<FormStatus> response) {
                    if (response.body()==null){
                        progressDialog.dismiss();
                        uploadPicsSumitButton.setClickable(true);
                        Toast.makeText(getActivity(),"responce null",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    uploadPicsSumitButton.setClickable(true);

                    progressDialog.dismiss();
//                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    HomeFragment fragment=new HomeFragment();
//                    transaction.replace(R.id.frameContainer, fragment);
//                    transaction.commit();
                }

                @Override
                public void onFailure(Call<FormStatus> call, Throwable t) {
                    progressDialog.dismiss();
                    uploadPicsSumitButton.setClickable(true);
                    Toast.makeText(getActivity(),"error"+call.toString(),Toast.LENGTH_SHORT).show();
                }
            });

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        if (permissionCheck){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        }else {
            requestMultiplePermissions();
        }

    }

    /*private void takePhotoFromCamera() {
        if (permissionCheck){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = CameraUtils.getOutputMediaFileUri(getContext()
            );//get fileUri from CameraUtils
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);//Send fileUri with intent
            startActivityForResult(intent, CAMERA);
        }else {
            requestMultiplePermissions();
        }


    }
*/
    /**
     * Capturing Camera Image will launch camera app requested image capture
     */
    private void takePhotoFromCamera() {
        if (permissionCheck){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }else {
            requestMultiplePermissions();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    vbs_upload_quotation_image.setImageBitmap(bmp);
                    imagesList.add(imageToString(bmp));
                    finalImage=imageToString(bmp);
                    upload();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                showCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

            /*if (requestCode == CAMERA) {

            try {
                //When image is captured successfully

                // Refreshing the gallery
                CameraUtils.refreshGallery(getContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                showCapturedImage();

            } catch (Exception e) {
                e.printStackTrace();
            }

            *//*bmp = (Bitmap) data.getExtras().get("data");
            Log.e("Capture ","Width  "+bmp.getWidth()+"  Height "+bmp.getHeight());
            vbs_upload_quotation_image.setImageBitmap(bmp);
            imagesList.add(imageToString(bmp));*//*
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();

        }*/
    }

    /*  Show Captured over ImageView  */
    private void showCapturedImage() {
        bmp = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
        Log.e("Camera ",""+bmp.getWidth()+"   "+ bmp.getHeight());
        vbs_upload_quotation_image.setImageBitmap(bmp);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        imagesList.add(Base64.encodeToString(byteArray,Base64.DEFAULT));
        finalImage= Base64.encodeToString(byteArray,Base64.DEFAULT);
        upload();
//        bmp.recycle();
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            permissionCheck=true;
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                            permissionCheck=false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    //upload images
    public String  imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 4, byteArrayOutputStream);
        byte[] imgbyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }


}
