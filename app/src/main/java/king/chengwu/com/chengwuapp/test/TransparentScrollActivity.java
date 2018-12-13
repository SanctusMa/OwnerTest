package king.chengwu.com.chengwuapp.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;

/**
 * @author shmyh
 */
public class TransparentScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter<Holder>() {
            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(TransparentScrollActivity.this)
                        .inflate(R.layout.transparent_item_view, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int i) {
                holder.tv.setText("第" + i + "个TextView");
                if (i == 3) {
                    holder.view.setVisibility(View.VISIBLE);
                } else {
                    holder.view.setVisibility(View.GONE);
                }
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    class Holder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.transparent_tv);
            view = itemView.findViewById(R.id.transparent_view);
        }
    }
}
