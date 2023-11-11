package com.example.ban_dt_lt.Xu_Ly_Danh_Sach;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ban_dt_lt.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_danh_sach_dat_hang extends RecyclerView.Adapter<adapter_danh_sach_dat_hang.UserViewHolder> {
    ArrayList<get_set_dat_hang> lstGetSet;
    Context context;

    public adapter_danh_sach_dat_hang(ArrayList<get_set_dat_hang> lstGetSet, Context context) {
        this.lstGetSet = lstGetSet;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_danh_sach_dat_hang.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_dat_hang,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_danh_sach_dat_hang.UserViewHolder holder, int position) {
        get_set_dat_hang item = lstGetSet.get(position);
        //
        holder.tvTen_san_pham_dat_hangC.setText(item.getTenSP());
        holder.tvSo_luong_dat_hangC.setText(item.getSoLuong());
        holder.tvTong_tien_dat_hangC.setText(item.getGiaTien());
        holder.tvTrang_thai_dat_hangC.setText(item.getMoTa());
        Picasso.get().load(item.getHinhAnh()).into(holder.ivHinh_anh_dat_hangC);
        holder.iv_xoa_san_pham_dat_hangC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xóa tài khoản tại vị trí hiện tại trong ArrayList
                                    get_set_dat_hang item = lstGetSet.get(position);
                                    lstGetSet.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lstGetSet.size());
                                    // Xóa tài khoản tương ứng trong Firebase Realtime Database
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Nguoi_Dung").child(item.getSo_dien_thoai()).child("DatHang").child(item.getTenSP());
                                    ref.removeValue();
                                    Toast.makeText(context, "Đã xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstGetSet.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen_san_pham_dat_hangC,tvSo_luong_dat_hangC,tvTong_tien_dat_hangC,tvTrang_thai_dat_hangC;
        ImageView iv_xoa_san_pham_dat_hangC,ivHinh_anh_dat_hangC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen_san_pham_dat_hangC = itemView.findViewById(R.id.tvTen_san_pham_dat_hang);
            tvSo_luong_dat_hangC = itemView.findViewById(R.id.tvSo_luong_dat_hang);
            tvTong_tien_dat_hangC = itemView.findViewById(R.id.tvTong_tien_dat_hang);
            tvTrang_thai_dat_hangC = itemView.findViewById(R.id.tvTrang_thai_dat_hang);
            iv_xoa_san_pham_dat_hangC = itemView.findViewById(R.id.iv_xoa_san_pham_dat_hang);
            ivHinh_anh_dat_hangC = itemView.findViewById(R.id.ivHinh_anh_dat_hang);
        }
    }
}
