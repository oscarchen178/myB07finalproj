package com.example.cscb07final;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    LoginActivity view;

    @Mock
    LoginModel model;

    @Test
    public void testPresenterValid() throws InterruptedException {

        String email = "d@gmail.com";
        String password = "password";
        String result = "Welcome";

        /** stubbing */
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.loginSuccessful()).thenReturn(result);

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.loginUser();

        /** verifying displayMessage with specific string value */
        verify(view).displayMessage(result);

        /*** Argument captors ***/
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).displayMessage(captor.capture());
        assertEquals(captor.getValue(), result);

        /*** Verifying order ***/
        InOrder order = inOrder(view, view, model, model, view, view);
        order.verify(view).getEmail();
        order.verify(view).getPassword();
        order.verify(model).loginAuth(email,password);
        order.verify(model).loginSuccessful();
        order.verify(view).displayMessage(result);
        order.verify(view).changeActivity();

    }

    @Test
    public void TestPresenterInvalidEmail() throws InterruptedException {

        String email = "bob";
        String password = "password";

        /** stubbing */
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.loginUser();

        /** verifying displayMessage with specific string value */
        verify(view).displayMessage("Need to input a valid email");

        /*** Argument captors ***/
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).displayMessage(captor.capture());
        assertEquals(captor.getValue(), "Need to input a valid email");

        /*** Verifying order ***/
        InOrder order = inOrder(view, view, view);
        order.verify(view).getEmail();
        order.verify(view).getPassword();
        order.verify(view).displayMessage("Need to input a valid email");

    }

    @Test
    public void TestPresenterEmptyEmail() throws InterruptedException {

        String email = "";
        String password = "password";

        /** stubbing */
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.loginUser();

        /** verifying displayMessage with specific string value */
        verify(view).displayMessage("Fields must not be empty");

        /*** Argument captors ***/
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).displayMessage(captor.capture());
        assertEquals(captor.getValue(), "Fields must not be empty");

        /*** Verifying order ***/
        InOrder order = inOrder(view, view, view);
        order.verify(view).getEmail();
        order.verify(view).getPassword();
        order.verify(view).displayMessage("Fields must not be empty");

    }

    @Test
    public void TestPresenterEmptyPassword() throws InterruptedException {

        String email = "a@gmail.com";
        String password = "";

        /** stubbing */
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.loginUser();

        /** verifying displayMessage with specific string value */
        verify(view).displayMessage("Fields must not be empty");

        /*** Argument captors ***/
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).displayMessage(captor.capture());
        assertEquals(captor.getValue(), "Fields must not be empty");

        /*** Verifying order ***/
        InOrder order = inOrder(view, view, view);
        order.verify(view).getEmail();
        order.verify(view).getPassword();
        order.verify(view).displayMessage("Fields must not be empty");

    }


    @Test
    public void TestPresenterInvalidPassword() throws InterruptedException {

        String email = "d@gmail.com";
        String password = "Mike Wazowski";
        String result = "";

        /** stubbing */
        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.loginSuccessful()).thenReturn(result);

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.loginUser();

        /** verifying displayMessage with specific string value */
        verify(view).displayMessage("Login Failed");

        /*** Argument captors ***/
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).displayMessage(captor.capture());
        assertEquals(captor.getValue(), "Login Failed");

        /*** Verifying order ***/
        InOrder order = inOrder(view, view, model, model, view);
        order.verify(view).getEmail();
        order.verify(view).getPassword();
        order.verify(model).loginAuth(email,password);
        order.verify(model).loginSuccessful();
        order.verify(view).displayMessage("Login Failed");

    }
}
