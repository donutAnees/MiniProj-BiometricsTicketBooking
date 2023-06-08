<!DOCTYPE html>
<html>
    <head>
        <title>Create Account</title>
        <link rel="stylesheet" href="createstyle.css"/>
    </head>
    <body>
        <div class="create-account-container">
            <div class="create-account-logo">
            <a href="login.jsp">
                <img src="bus.png" alt="logo">
            </a>
            </div>
            <div class="create-account">
                <h1>Create Account</h1>
            <form action="register" method="post" class="create-account-form" enctype='multipart/form-data'>
                <div class="create-account-name">
                    <input type="text" placeholder="First Name" name="firstname" id="firstname">
                    <input type="text" placeholder="Last Name"name="lastname" id="lastname">
                </div>
                <div class="create-account-username">
                    <input type="text" placeholder="Username" name="username" id="username">
                </div>
                <div class="create-account-fingerprint">
                    <h3>Attach Your Fingerprint</h3>
                    <label for="inputTag">
                        <input id="inputTag" type="file" name="file"/>
                        <img src="fingerprint.png" alt="fingerprint">
                      </label>
                </div>
                <div class="create-account-password">
                    <input type="text" placeholder="Password" name="password" id="password">
                </div>
            <div class="align-button">
                <button type="submit">Sign Up</button>
            </div>
            </form>
            </div>
        </div>
    </body>
</html>