package com.example.ban_dt_lt.Quan_Tri_Vien;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ban_dt_lt.R;
import com.example.ban_dt_lt.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_lt.Xu_Ly_Danh_Sach.adapter_danh_sach_tai_khoan;
import com.example.ban_dt_lt.Xu_Ly_Danh_Sach.get_set_nguoi_dung;
import com.example.ban_dt_lt.databinding.ActivityDanhSachTaiKhoanBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachTaiKhoan extends DrawerBaseActivity {
    ActivityDanhSachTaiKhoanBinding activityDanhSachTaiKhoanBinding;
    RecyclerView rvListC;
    ArrayList<get_set_nguoi_dung> lstGetSetC;
    adapter_danh_sach_tai_khoan adapter_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDanhSachTaiKhoanBinding = ActivityDanhSachTaiKhoanBinding.inflate(getLayoutInflater());
        setContentView(activityDanhSachTaiKhoanBinding.getRoot());
        allocateActivityTitle("Danh sách tài khoản");
        //
        Anhxa();
        //
        rvListC = findViewById(R.id.rvList_danh_sach_tai_khoan);
        lstGetSetC = new ArrayList<>();
        adapter_recyclerview = new adapter_danh_sach_tai_khoan(lstGetSetC,this);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(DanhSachTaiKhoan.this,LinearLayoutManager.VERTICAL,false);
        rvListC.setAdapter(adapter_recyclerview);
        rvListC.setLayoutManager(linearLayoutManager);
    }

    private void Anhxa() {
        // Lấy dữ liệu từ Firebase Realtime Database
        DatabaseReference nguoiDungRef = FirebaseDatabase.getInstance().getReference("Nguoi_Dung");
        nguoiDungRef.orderByChild("so_dien_thoai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String ten = userSnapshot.child("ten").getValue(String.class);
                    String so_dien_thoai = userSnapshot.child("so_dien_thoai").getValue(String.class);
                    String mat_khau = userSnapshot.child("mat_khau").getValue(String.class);

                    get_set_nguoi_dung nguoiDung = new get_set_nguoi_dung(ten,so_dien_thoai,mat_khau,"","");
                    lstGetSetC.add(nguoiDung);
                    // Log.d("FirebaseData", "Data: " + lstGetSetC.toString());
                }
                adapter_recyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}