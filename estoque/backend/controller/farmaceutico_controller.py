from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.farmaceutico_orm import FarmaceuticoORM
from schema.farmaceutico_schema import FarmaceuticoUpdate
from schema.farmaceutico_schema import FarmaceuticoCreate


def criar_farmaceutico(dados: FarmaceuticoCreate) -> FarmaceuticoORM:
    db: Session = SessionLocal()
    restrito = (
        dados.tarja.lower() in ["vermelha", "preta"]
        or dados.retencao_de_receita
    )
    novo = FarmaceuticoORM(
        nome=dados.nome,
        categoria=dados.categoria,
        quantidade=dados.quantidade,
        valor=dados.valor,
        tarja=dados.tarja,
        composicao=dados.composicao,
        retencao_de_receita=dados.retencao_de_receita,
        generico=dados.generico,
        receita=dados.receita,
        restrito=restrito,
        filial_id=dados.filial_id
    )
    db.add(novo)
    db.commit()
    db.refresh(novo)
    return novo


def listar_farmaceuticos() -> list[FarmaceuticoORM]:
    db: Session = SessionLocal()
    return db.query(FarmaceuticoORM).all()


def buscar_farmaceutico_por_id(id: int) -> FarmaceuticoORM | None:
    db: Session = SessionLocal()
    return db.query(FarmaceuticoORM).filter(FarmaceuticoORM.id == id).first()


def buscar_farmaceutico_por_nome(nome: str) -> list[FarmaceuticoORM]:
    db: Session = SessionLocal()
    return db.query(FarmaceuticoORM).filter(FarmaceuticoORM.nome.ilike(f"%{nome}%")).all()


def remover_farmaceutico(id: int) -> bool:
    db: Session = SessionLocal()
    farmaceutico = db.query(FarmaceuticoORM).filter(FarmaceuticoORM.id == id).first()
    if farmaceutico:
        db.delete(farmaceutico)
        db.commit()
        return True
    return False


def atualizar_farmaceutico(id: int, dados: FarmaceuticoUpdate):
    db: Session = SessionLocal()
    farmaceutico = db.query(FarmaceuticoORM).filter(FarmaceuticoORM.id == id).first()
    if not farmaceutico:
        return None

    for campo, valor in dados.dict().items():
        setattr(farmaceutico, campo, valor)

    farmaceutico.restrito = farmaceutico.tarja.lower() in ["vermelha", "preta"]
    db.commit()
    db.refresh(farmaceutico)
    return farmaceutico
