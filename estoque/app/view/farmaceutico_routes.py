from fastapi import APIRouter, HTTPException
from schema.farmaceutico_schema import FarmaceuticoCreate, FarmaceuticoResponse
from controller import farmaceutico_controller


router = APIRouter(prefix="/farmaceutico", tags=["Farmaceutico"])


@router.post("/", response_model=FarmaceuticoResponse)
def criar(dados: FarmaceuticoCreate):
    novo = farmaceutico_controller.criar_farmaceutico(dados)
    return novo


@router.get("/", response_model=list[FarmaceuticoResponse])
def listar():
    return farmaceutico_controller.listar_farmaceuticos()


@router.get("/{id}", response_model=FarmaceuticoResponse)
def buscar(id: int):
    f = farmaceutico_controller.buscar_farmaceutico_por_id(id)
    if f:
        return f
    raise HTTPException(status_code=404, detail="Farmacêutico não encontrado")


@router.delete("/{id}")
def deletar(id: int):
    if farmaceutico_controller.remover_farmaceutico(id):
        return {"mensagem": "Farmacêutico removido com sucesso"}
    raise HTTPException(status_code=404, detail="Farmacêutico não encontrado")
