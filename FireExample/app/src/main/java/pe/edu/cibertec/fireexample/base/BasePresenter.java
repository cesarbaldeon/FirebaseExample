package pe.edu.cibertec.fireexample.base;

public interface BasePresenter<V extends BaseView> {

    void attach(V view);

    void detach();
}