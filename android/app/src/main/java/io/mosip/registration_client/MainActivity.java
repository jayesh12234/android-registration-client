/*
 * Copyright (c) Modular Open Source Identity Platform
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
*/

package io.mosip.registration_client;

import io.mosip.registration.clientmanager.constant.ClientManagerConstant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.mosip.registration.clientmanager.config.AppModule;
import io.mosip.registration.clientmanager.config.NetworkModule;
import io.mosip.registration.clientmanager.config.RoomModule;
import io.mosip.registration.clientmanager.constant.AuditEvent;
import io.mosip.registration.clientmanager.constant.Components;
import io.mosip.registration.clientmanager.constant.PacketClientStatus;
import io.mosip.registration.clientmanager.constant.PacketTaskStatus;
import io.mosip.registration.clientmanager.dao.GlobalParamDao;
import io.mosip.registration.clientmanager.entity.GlobalParam;
import io.mosip.registration.clientmanager.entity.Registration;
import io.mosip.registration.clientmanager.entity.SyncJobDef;
import io.mosip.registration.clientmanager.repository.GlobalParamRepository;
import io.mosip.registration.clientmanager.repository.IdentitySchemaRepository;
import io.mosip.registration.clientmanager.repository.RegistrationCenterRepository;
import io.mosip.registration.clientmanager.repository.SyncJobDefRepository;
import io.mosip.registration.clientmanager.repository.UserDetailRepository;
import io.mosip.registration.clientmanager.service.LoginService;
import io.mosip.registration.clientmanager.spi.AsyncPacketTaskCallBack;
import io.mosip.registration.clientmanager.spi.AuditManagerService;
import io.mosip.registration.clientmanager.spi.JobManagerService;
import io.mosip.registration.clientmanager.spi.JobTransactionService;
import io.mosip.registration.clientmanager.spi.MasterDataService;
import io.mosip.registration.clientmanager.spi.PacketService;
import io.mosip.registration.clientmanager.spi.RegistrationService;
import io.mosip.registration.clientmanager.spi.SyncRestService;
import io.mosip.registration.clientmanager.util.SyncRestUtil;
import io.mosip.registration.keymanager.spi.ClientCryptoManagerService;

import io.mosip.registration.transliterationmanager.service.TransliterationServiceImpl;
import io.mosip.registration_client.api_services.AuditDetailsApi;
import io.mosip.registration_client.api_services.AuthenticationApi;
import io.mosip.registration_client.api_services.BiometricsDetailsApi;
import io.mosip.registration_client.api_services.CommonDetailsApi;
import io.mosip.registration_client.api_services.DashBoardDetailsApi;
import io.mosip.registration_client.api_services.DemographicsDetailsApi;
import io.mosip.registration_client.api_services.DocumentCategoryApi;
import io.mosip.registration_client.api_services.DocumentDetailsApi;
import io.mosip.registration_client.api_services.DynamicDetailsApi;
import io.mosip.registration_client.api_services.MachineDetailsApi;
import io.mosip.registration_client.api_services.PacketAuthenticationApi;
import io.mosip.registration_client.api_services.MasterDataSyncApi;
import io.mosip.registration_client.api_services.ProcessSpecDetailsApi;
import io.mosip.registration_client.api_services.RegistrationApi;
import io.mosip.registration_client.api_services.TransliterationApi;
import io.mosip.registration_client.api_services.UserDetailsApi;
import io.mosip.registration_client.model.AuditResponsePigeon;
import io.mosip.registration_client.model.AuthResponsePigeon;
import io.mosip.registration_client.model.BiometricsPigeon;
import io.mosip.registration_client.model.CommonDetailsPigeon;
import io.mosip.registration_client.model.DashBoardPigeon;
import io.mosip.registration_client.model.DemographicsDataPigeon;
import io.mosip.registration_client.model.DocumentCategoryPigeon;
import io.mosip.registration_client.model.DynamicResponsePigeon;
import io.mosip.registration_client.model.MachinePigeon;
import io.mosip.registration_client.model.PacketAuthPigeon;
import io.mosip.registration_client.model.MasterDataSyncPigeon;
import io.mosip.registration_client.model.ProcessSpecPigeon;
import io.mosip.registration_client.model.RegistrationDataPigeon;
import io.mosip.registration_client.model.TransliterationPigeon;
import io.mosip.registration_client.model.UserPigeon;
import io.mosip.registration_client.model.DocumentDataPigeon;
import io.mosip.registration_client.utils.BatchJob;
import io.mosip.registration_client.utils.CustomToast;

import android.net.Uri;


public class MainActivity extends FlutterActivity {
    private static final String REG_CLIENT_CHANNEL = "com.flutter.dev/io.mosip.get-package-instance";

    ObjectWriter ow;
    @Inject
    ClientCryptoManagerService clientCryptoManagerService;
    @Inject
    SyncRestUtil syncRestFactory;
    @Inject
    SyncRestService syncRestService;
    @Inject
    LoginService loginService;
    @Inject
    AuditManagerService auditManagerService;
    @Inject
    MasterDataService masterDataService;

    @Inject
    RegistrationService registrationService;
    @Inject
    PacketService packetService;
    @Inject
    JobTransactionService jobTransactionService;
    @Inject
    JobManagerService jobManagerService;
    @Inject
    IdentitySchemaRepository identitySchemaRepository;
    @Inject
    UserDetailRepository userDetailRepository;
    @Inject
    RegistrationCenterRepository registrationCenterRepository;

    @Inject
    GlobalParamRepository globalParamRepository;

    @Inject
    MachineDetailsApi machineDetailsApi;

    @Inject
    UserDetailsApi userDetailsApi;

    @Inject
    CommonDetailsApi commonDetailsApi;

    @Inject
    AuthenticationApi authenticationApi;

    @Inject
    ProcessSpecDetailsApi processSpecDetailsApi;

    @Inject
    BiometricsDetailsApi biometricsDetailsApi;

    @Inject
    PacketAuthenticationApi packetAuthenticationApi;

    @Inject
    RegistrationApi registrationApi;

    @Inject
    DemographicsDetailsApi demographicsDetailsApi;

    @Inject
    DocumentDetailsApi documentDetailsApi;

    @Inject
    DynamicDetailsApi dynamicDetailsApi;

    @Inject
    MasterDataSyncApi masterDataSyncApi;
    @Inject
    AuditDetailsApi auditDetailsApi;

    @Inject
    SyncJobDefRepository syncJobDefRepository;

    @Inject
    GlobalParamDao globalParamDao;

    @Inject
    DocumentCategoryApi documentCategoryApi;

    @Inject
    DashBoardDetailsApi dashBoardDetailsApi;

    @Inject
    BatchJob batchJob;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("REGISTRATION_PACKET_UPLOAD")) {
            batchJob.syncRegistrationPackets(context);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(()-> {
                createBackgroundTask("registrationPacketUploadJob");
            }, 1, TimeUnit.MINUTES);
        }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBackgroundTask("registrationPacketUploadJob");
        IntentFilter intentFilterUpload = new IntentFilter("REGISTRATION_PACKET_UPLOAD");
        registerReceiver(broadcastReceiver, intentFilterUpload);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    void createBackgroundTask(String api){
        Intent intent = new Intent(this, UploadBackgroundService.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getForegroundService(
                    this,
                    0,  // Request code
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );
        } else {
            pendingIntent = PendingIntent.getService(
                    this,
                    0,  // Request code
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                Intent permissionIntent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                permissionIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(permissionIntent);
            }
            long alarmTime = batchJob.getIntervalMillis(api);
            long currentTime = System.currentTimeMillis();
            long delay = alarmTime > currentTime ? alarmTime - currentTime : alarmTime - currentTime;
            Log.d(getClass().getSimpleName(), String.valueOf(delay)+ " Next Execution");

//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 30000, pendingIntent);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, pendingIntent);
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, pendingIntent);
            }
        }
    }

    public void initializeAppComponent() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .application(getApplication())
                .networkModule(new NetworkModule(getApplication()))
                .roomModule(new RoomModule(getApplication(), getApplicationInfo()))
                .appModule(new AppModule(getApplication()))
                .hostApiModule(new HostApiModule(getApplication()))
                .build();
        appComponent.inject(this);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        initializeAppComponent();
        MachinePigeon.MachineApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), machineDetailsApi);
        UserPigeon.UserApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), userDetailsApi);
        CommonDetailsPigeon.CommonDetailsApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(),commonDetailsApi);
        AuthResponsePigeon.AuthResponseApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), authenticationApi);
        ProcessSpecPigeon.ProcessSpecApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), processSpecDetailsApi);
        BiometricsPigeon.BiometricsApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(),biometricsDetailsApi);
        biometricsDetailsApi.setCallbackActivity(this);
        RegistrationDataPigeon.RegistrationDataApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), registrationApi);
        PacketAuthPigeon.PacketAuthApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), packetAuthenticationApi);
        packetAuthenticationApi.setCallbackActivity(this);
        DemographicsDataPigeon.DemographicsApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), demographicsDetailsApi);
        DocumentDataPigeon.DocumentApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), documentDetailsApi);
        DocumentCategoryPigeon.DocumentCategoryApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), documentCategoryApi);
        DashBoardPigeon.DashBoardApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), dashBoardDetailsApi);

        TransliterationPigeon.TransliterationApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(),new TransliterationApi(new TransliterationServiceImpl()));
        DynamicResponsePigeon.DynamicResponseApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), dynamicDetailsApi);
        batchJob.setCallbackActivity(this);
        MasterDataSyncPigeon.SyncApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), masterDataSyncApi);
        masterDataSyncApi.setCallbackActivity(this, batchJob);
        AuditResponsePigeon.AuditResponseApi.setup(flutterEngine.getDartExecutor().getBinaryMessenger(), auditDetailsApi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    biometricsDetailsApi.parseDiscoverResponse(data.getExtras());
                    break;
                case 2:
                    biometricsDetailsApi.parseDeviceInfoResponse(data.getExtras());
                    break;
                case 3:
                    biometricsDetailsApi.parseRCaptureResponse(data.getExtras());
                    break;
            }
        }
    }
}
