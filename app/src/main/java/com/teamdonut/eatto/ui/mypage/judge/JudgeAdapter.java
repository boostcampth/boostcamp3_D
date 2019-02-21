package com.teamdonut.eatto.ui.mypage.judge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MypageJudgeItemBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class JudgeAdapter extends BaseRecyclerViewAdapter<Board, JudgeAdapter.ViewHolder> {
    public JudgeAdapter(List<Board> dataSet) {
        super(dataSet);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_judge_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MypageJudgeItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}