package com.teamdonut.eatto.ui.mypage.judge;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MypageJudgeItemBinding;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class JudgeAdapter extends BaseRecyclerViewAdapter<Board, JudgeAdapter.ViewHolder> {

    private MyPageJudgeViewModel mViewModel;
    private JudgeItemActionListener listener;

    public JudgeAdapter(List<Board> dataSet, MyPageJudgeViewModel myPageJudgeViewModel) {
        super(dataSet);
        mViewModel = myPageJudgeViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MypageJudgeItemBinding binding = MypageJudgeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        listener = new JudgeItemActionListener() {
            @Override
            public void onGreatClick(Board board, int score) {
                mViewModel.sendJudgeResult(board, score);
            }

            @Override
            public void onGoodClick(Board board, int score) {
                mViewModel.sendJudgeResult(board, score);
            }

            @Override
            public void onNormalClick(Board board, int score) {
                mViewModel.sendJudgeResult(board, score);
            }
        };

        binding.setListener(listener);
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