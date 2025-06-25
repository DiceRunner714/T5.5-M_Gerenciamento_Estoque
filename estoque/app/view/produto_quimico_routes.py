from fastapi import APIRouter, HTTPException
from schema.produto_quimico_schema import ProdutoQuimicoCreate, ProdutoQuimicoResponse
from controller import produto_quimico_controller

router = APIRouter(prefix="/produto_quimico", tags=["Produto Químico"])

@router.post("/", response_model=ProdutoQuimicoResponse)
def criar(dados: ProdutoQuimicoCreate):
    try:
        return produto_quimico_controller.criar_produto_quimico(dados)
    except Exception as e:
        print("Erro ao criar produto químico:", e)
        raise HTTPException(status_code=500, detail=str(e))

@router.get("/", response_model=list[ProdutoQuimicoResponse])
def listar():
    return produto_quimico_controller.listar_produtos()

@router.get("/{id}", response_model=ProdutoQuimicoResponse)
def buscar(id: int):
    produto = produto_quimico_controller.buscar_produto_por_id(id)
    if produto:
        return produto
    raise HTTPException(status_code=404, detail="Produto não encontrado")

@router.delete("/{id}")
def deletar(id: int):
    if produto_quimico_controller.remover_produto(id):
        return {"mensagem": "Produto removido com sucesso"}
    raise HTTPException(status_code=404, detail="Produto não encontrado")
