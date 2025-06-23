from fastapi import APIRouter, HTTPException
from schema.filial_schema import FilialCreate, FilialResponse
from controller import filial_controller

router = APIRouter(prefix="/filial", tags=["Filial"])

@router.post("/", response_model=FilialResponse)
def criar_filial(dados: FilialCreate):
    nova = filial_controller.criar_filial(dados.nome, dados.empresa_id)
    return FilialResponse(id=nova.id, nome=nova.nome, empresa_id=nova.empresa_id)

@router.get("/", response_model=list[FilialResponse])
def listar_filiais():
    filiais = filial_controller.listar_filiais()
    return [FilialResponse(id=f.id, nome=f.nome, empresa_id=f.empresa_id) for f in filiais]

@router.get("/{id}", response_model=FilialResponse)
def buscar_filial(id: int):
    f = filial_controller.buscar_filial_por_id(id)
    if f:
        return FilialResponse(id=f.id, nome=f.nome, empresa_id=f.empresa_id)
    raise HTTPException(status_code=404, detail="Filial não encontrada")

@router.delete("/{id}")
def deletar_filial(id: int):
    if filial_controller.remover_filial(id):
        return {"mensagem": "Filial removida com sucesso"}
    raise HTTPException(status_code=404, detail="Filial não encontrada")
