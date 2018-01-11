package fr.centrale.projetnews.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.centrale.projetnews.Adapters.SourceAdapter;
import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SourceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SourceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SourceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SourceAdapter sourceAdapter;

    public SourceFragment() {
        // Required empty public constructor
    }

    public static SourceFragment newInstance() {
        SourceFragment fragment = new SourceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.source_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.recyclerview);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<NewsSource> sources = ((NewsApplication) getActivity().getApplication()).getSources();

        sourceAdapter = new SourceAdapter(sources);
        rv.setAdapter(sourceAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSourceFragmentInteraction(String sourceId);
    }

    public void notifyDataSetChanged(){
        if(sourceAdapter != null)
            sourceAdapter.notifyDataSetChanged();
    }
}
