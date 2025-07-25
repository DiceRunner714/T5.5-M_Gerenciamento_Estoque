from fastapi import APIRouter, HTTPException
from schema.filial_schema import FilialCreate, FilialResponse
from controller import filial_controller


router = APIRouter(prefix="/filial", tags=["Filial"])


@router.post("/", response_model=FilialResponse)
def criar_filial(dados: FilialCreate):
    nova = filial_controller.criar_filial(dados.nome, dados.local, dados.empresa_id)
    return FilialResponse(
        id=nova.id,
        nome=nova.nome,
        local=nova.local,
        empresa_id=nova.empresa_id
    )


@router.get("/", response_model=list[FilialResponse])
def listar_filiais(empresa_id: int = None):
    filiais = filial_controller.listar_filiais(empresa_id)

    return [
        FilialResponse(
            id=f.id,
            nome=f.nome,
            local=f.local,
            empresa_id=f.empresa_id
        ) for f in filiais
    ]


@router.get("/{id}", response_model=FilialResponse)
def buscar_filial(id: int):
    f = filial_controller.buscar_filial_por_id(id)
    if f:
        return FilialResponse(
            id=f.id,
            nome=f.nome,
            local=f.local,
            empresa_id=f.empresa_id
        )
    raise HTTPException(status_code=404, detail="Filial não encontrada")


@router.delete("/{id}")
def deletar_filial(id: int):
    if filial_controller.remover_filial(id):
        return {"mensagem": "Filial removida com sucesso"}
    raise HTTPException(status_code=404, detail="Filial não encontrada")


@router.get("/{id}/estoque")
def estoque_filial(id: int):
    resultado = filial_controller.calcular_estoque_total_filial(id)
    if resultado:
        return resultado
    raise HTTPException(status_code=404, detail="Filial não encontrada ou sem estoque")


@router.get("/{id}/itens-zerados")
def itens_zerados_filial(id: int):
    itens = filial_controller.listar_itens_zerados_filial(id)
    return {"filial_id": id, "itens_zerados": itens}
