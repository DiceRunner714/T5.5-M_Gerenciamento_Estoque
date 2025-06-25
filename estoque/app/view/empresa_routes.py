from fastapi import APIRouter, HTTPException
from schema.empresa_schema import EmpresaCreate, EmpresaResponse
from controller import empresa_controller


router = APIRouter(prefix="/empresa", tags=["Empresa"])


@router.post("/", response_model=EmpresaResponse)
def criar_empresa(dados: EmpresaCreate):
    nova = empresa_controller.criar_empresa(dados.nome)
    return EmpresaResponse(id=nova.id, nome=nova.nome)


@router.get("/", response_model=list[EmpresaResponse])
def listar_empresas():
    empresas = empresa_controller.listar_empresas()
    return [EmpresaResponse(id=e.id, nome=e.nome) for e in empresas]


@router.get("/{id}", response_model=EmpresaResponse)
def buscar_empresa(id: int):
    empresa = empresa_controller.buscar_empresa_por_id(id)
    if empresa:
        return EmpresaResponse(id=id, nome=empresa.nome)
    raise HTTPException(status_code=404, detail="Empresa não encontrada")


@router.delete("/{id}")
def deletar_empresa(id: int):
    sucesso = empresa_controller.remover_empresa(id)
    if sucesso:
        return {"mensagem": "Empresa removida com sucesso"}
    raise HTTPException(status_code=404, detail="Empresa não encontrada")
