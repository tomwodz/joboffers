package pl.tomwodz.joboffers.domain.loginandregister;

enum MessageResponse {
        USER_NOT_FOUND("User not found :"),
         USERNAME_ALREADY_EXISTS("Username is already busy: ");

        final String info;

        MessageResponse(String info) {
            this.info = info;
        }

}
