from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.farmaceutico_orm import FarmaceuticoORM


def criar_farmaceutico(dados) -> FarmaceuticoORM:
    db: Session = SessionLocal()
    novo = FarmaceuticoORM(
        nome=dados.nome,
        crf=dados.crf,
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


def remover_farmaceutico(id: int) -> bool:
    db: Session = SessionLocal()
    farmaceutico = db.query(FarmaceuticoORM).filter(FarmaceuticoORM.id == id).first()
    if farmaceutico:
        db.delete(farmaceutico)
        db.commit()
        return True
    return False
