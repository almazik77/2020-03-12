<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Авторизация</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <h5 class="card-title text-center">Sign In</h5>

                    <form action="/signIn" method="post" class="form-signin">
                        <div class="form-label-group">
                            <input type="text" name="email" placeholder="Email" class="form-control">
                        </div>

                        <div class="form-label-group">
                            <input type="password" name="password" placeholder="password" class="form-control">
                        </div>

                        <div class="custom-control custom-checkbox mb-3">
                            <input type="checkbox" class="custom-control-input" id="customCheck1">
                            <label class="custom-control-label" for="customCheck1">Remember password</label>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#if error??>
    <div class="alert alert-danger" role="alert">Логин или пароль введены неверно</div>
    <div class="alert" role="alert">Или вы не подтвердили почту</div>
</#if>
<div>
</div>
</body>
</html>
