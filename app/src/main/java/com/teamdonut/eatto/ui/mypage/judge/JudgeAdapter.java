package com.teamdonut.eatto.ui.mypage.judge;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MypageJudgeItemBinding;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class JudgeAdapter extends BaseRecyclerViewAdapter<Board, JudgeAdapter.ViewHolder> {

    private MyPageJudgeViewModel viewModel;
    private JudgeItemActionListener judgeItemActionListener;

    public JudgeAdapter(List<Board> dataSet, MyPageJudgeViewModel myPageJudgeViewModel) {
        super(dataSet);
        this.viewModel = myPageJudgeViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MypageJudgeItemBinding binding = MypageJudgeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        judgeItemActionListener = (board, score) -> {
            viewModel.sendJudgeResult(board, score);
        };

        binding.setListener(judgeItemActionListener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private MypageJudgeItemBinding binding;

        public ViewHolder(MypageJudgeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}