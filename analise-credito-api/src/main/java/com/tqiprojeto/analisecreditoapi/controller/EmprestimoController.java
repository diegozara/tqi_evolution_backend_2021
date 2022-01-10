package com.tqiprojeto.analisecreditoapi.controller;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoDataPrimeiraParcelaExcedida;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoParcelasExcedida;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoDetalhado;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoSimples;
import com.tqiprojeto.analisecreditoapi.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see EmprestimoService
 * @since Release 1.0
 */
@RestController
@RequestMapping("/api/v1/emprestimos") //Endpoint para as funcionalidades referente a Classe Emprestimo
public class EmprestimoController {

    private EmprestimoService emprestimoService;

    /**
     * Construtor da Classe
     *
     * @param emprestimoService     Objeto de acesso da classe EmprestimoService
     */
    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    /**
     * método GET para listar todos os emprestimos cadastrados de todos os clientes
     *
     * @return lista de todos os emprestimos cadastrados
     */
    @GetMapping
    public List<Emprestimo> listarTodosEmprestimos(){
        return emprestimoService.listarTodosEmprestimos();
    }

    /**
     * Método GET para listagem simples dos emprestimos, retornando os campos id, valor e quantidade de parcelas do empréstimo.
     *
     * Essa listagem é específica de um determinado cliente, sendo informado o id do CLIENTE para a busca
     * dos emprestimos feitos por ele.
     *
     * @param id    ID do Cliente que será informado para realizar a busca dos emprestimos feitos por ele
     * @return      Lista Simples com os campos id, valor e quantidade de parcelas da classe Emprestimo (vinculado ao id do cliente)
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
    @GetMapping("/clientes/{id}")
    public List<EmprestimoRetornoSimples> listarEmprestimos(@PathVariable Integer id) throws EmprestimoNaoCadastradoException {

        return emprestimoService.listarSimples(id);
    }

    /**
     * Método GET para listagem mais detalhada dos emprestimos, retornando os campos
     * id, valor, quantidade de parcelas, data da primeira parcela  do empréstimo e e-mail e renda do cliente vinculado aquele emprestimo.
     *
     * Essa listagem é específica de um determinado cliente, sendo informado o id do CLIENTE para a busca
     * dos emprestimos feitos por ele.
     *
     * @param id    ID do Cliente que será informado para realizar a busca dos emprestimos feitos por ele
     *
     * @return      Lista Detalhada com os campos id, valor, quantidade de parcelas, data da primeira parcela  do empréstimo
     * e e-mail e renda do cliente vinculado aquele emprestimo
     *
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
    @GetMapping("/clientes/detalhado/{id}")
    public List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (@PathVariable Integer id) throws  EmprestimoNaoCadastradoException{
        return emprestimoService.listarEmprestimoDetalhado(id);
    }

    /**
     * Lista de um emprestimo buscando por ID
     *
     * @param id    Código do emprestimo
     * @return      Lista do emprestimo referente ao ID informado
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
    @GetMapping("/{id}")
    public Emprestimo buscarPorId(@PathVariable Integer id) throws EmprestimoNaoCadastradoException {
        return emprestimoService.buscarPorId(id);
    }

    /**
     * Cadastro de emprestimos
     *
     * @param emprestimo    Payload dos dados para cadastro do emprestimo
     * @return              Retorna os campos do emprestimo criado
     * @throws EmprestimoParcelasExcedida       Se a quantidade de parcelas forem superior a 60
     * @throws ParseException                   Se o formato da data da primeira parcela for inválido
     * @throws EmprestimoDataPrimeiraParcelaExcedida    Se a data da primeira parcela for superior a 3 meses da data da solicitação
     * @throws ClienteNaoCadastradoException            Se não houver cliente cadastrado,
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo solicitar (@RequestBody Emprestimo emprestimo ) throws EmprestimoParcelasExcedida, ParseException, EmprestimoDataPrimeiraParcelaExcedida, ClienteNaoCadastradoException {
        return emprestimoService.solicitar(emprestimo);
    }

    /**
     * Atualizar algum dado do emprestimo cadastrado
     *
     * @param id    Id do emprestimo
     * @param emprestimo        Payload dos dados para cadastro do emprestimo
     * @return                  Retorna os campos do objeto atualizado
     * @throws EmprestimoNaoCadastradoException Se não houver nenhum emprestimo cadastrado com aquele ID.
     * @throws ClienteNaoCadastradoException    Se não houver nenhum cliente cadastrado
     * @throws EmprestimoParcelasExcedida       Se exceder a quantidade maxima de 60 parcelas
     *
     */
    @PutMapping ("/{id}")
    public Emprestimo atualizar (@PathVariable Integer id, @RequestBody Emprestimo emprestimo) throws EmprestimoNaoCadastradoException, ClienteNaoCadastradoException, EmprestimoDataPrimeiraParcelaExcedida {
        return emprestimoService.atualizar(id,emprestimo);
    }

    /**
     * Excluir um emprestimo
     *
     * @param id    ID do emprestimo a ser excluido
     * @throws EmprestimoNaoCadastradoException  Se não houver nenhum emprestimo cadastrado com aquele ID.
     */
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Integer id) throws EmprestimoNaoCadastradoException{
        emprestimoService.deletar(id);
    }

}
