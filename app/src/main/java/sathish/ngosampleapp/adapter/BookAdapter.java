package sathish.ngosampleapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sathish.ngosampleapp.R;
import sathish.ngosampleapp.dto.BookModel;
import sathish.ngosampleapp.util.Const;
import sathish.ngosampleapp.util.Util;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context mContext;
    private List<BookModel> bookList;

    public BookAdapter(Context mContext, List<BookModel> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final BookModel bookModel = bookList.get(position);
        holder.name.setText(bookModel.getName());
        holder.author.setText(" - " + bookModel.getAuthor());
        holder.edition.setText("(" + bookModel.getEdition() + ")");
        holder.editionNumber.setText("(" + bookModel.getEditionNumber() + ")");
        holder.isbn.setText(bookModel.getIsbn());
        holder.publisher.setText(bookModel.getPublisher());
        holder.noOfPages.setText(bookModel.getNoOfPages());
        holder.flipkartLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.openURLinBrowser(mContext, bookModel.getFlipkartLink());
            }
        });
        holder.amazonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.openURLinBrowser(mContext, bookModel.getAmazonLink());
            }
        });
        Glide.with(mContext).load(Const.BOOK_BANNER_PATH + bookModel.getBanner())
                .thumbnail(0.5f)
                .into(holder.banner);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_name)
        TextView name;
        @BindView(R.id.book_isbn)
        TextView isbn;
        @BindView(R.id.book_author)
        TextView author;
        @BindView(R.id.book_publisher)
        TextView publisher;
        @BindView(R.id.book_edition)
        TextView edition;
        @BindView(R.id.book_edition_no)
        TextView editionNumber;
        @BindView(R.id.book_no_of_page)
        TextView noOfPages;
        @BindView(R.id.book_flipkart)
        ImageView flipkartLink;
        @BindView(R.id.book_amazon)
        ImageView amazonLink;
        @BindView(R.id.book_banner)
        ImageView banner;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
