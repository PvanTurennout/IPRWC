import {MatDialog} from '@angular/material/dialog';
import {WarningDialogComponent} from '../../shared/dialogs/warning-dialog.component';

export function handleHttpErrors(err) {
  console.log(err);

  let errorMessage = 'Error';

  if (!err.error.code) {
    return errorMessage;
  }

  switch (err.error.code){
    case 400:
      errorMessage = returnMessageIfNotEmpty(err.error.message, true);
      break;
    case 404:
      errorMessage = returnMessageIfNotEmpty(err.error.message, false);
      break;
    case 409:
      errorMessage = 'Duplicate Error';
      break;
    case 500:
      errorMessage = 'Server Error';
      break;
  }

  return errorMessage;
}

function returnMessageIfNotEmpty(message: string, fourOO: boolean) {
  if (message){
    return message;
  }

  if (fourOO) {
    return 'Invalid Data!';
  } else {
    return 'Not Found';
  }
}

export function handleHttpAuthErrors(err) {
  console.error(err);

  let errorMessage = 'Error';

  if (!err.error.code) {
    return errorMessage;
  }

  switch (err.error.code){
    case 400:
      errorMessage = err.error.message;
      break;
    case 401:
      errorMessage = 'Invalid Credentials. Please, try again.';
      break;
    case 404:
      errorMessage = err.error.message;
      break;
    case 409:
      errorMessage = 'Email already registered';
      break;
    case 500:
      errorMessage = 'Server Error';
      break;
  }

  return errorMessage;
}

export function displayHttpError(message: string, dialog: MatDialog) {
  dialog.open(WarningDialogComponent, {data: {warningMessage: message}});
}

export function emptyFunction() {}
