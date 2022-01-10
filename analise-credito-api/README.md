<h2>API desenvolvida para atendar os seguintes requisitos:</h2>


Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:

Cadastro de clientes:
* O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.


Login:
* A autenticação será realizada por e-mail e senha.


Solicitação de empréstimo:

* Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.
O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.

Acompanhamento das solicitações de empréstimo:
* O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
* Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
* No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

Ao iniciar a API você deverá criar primeiramente um usuário e, após a criação desse usuário deverá fazer a autenticação do
token através do email e senha desse usuário cadastrado.

Para cadastrar clientes, utilize esse endpoint
```
http://localhost:8080/api/v1/clientes
```

Para gerar o token utilize esse endpoint, método POST (forneça as informações de email e senha do cliente que acabou de cadastrar)
```
http://localhost:8080/login
```

Para validar o Token acesse no postman a aba Headers: campo KEY deve ser marcado Authorization e VALUE deve ser escrito Bearer TOKEN_gerado_na_tela_de_login


_**Projeto proposto pela TQI_Evolution**_