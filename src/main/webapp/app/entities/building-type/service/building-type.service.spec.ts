import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBuildingType, BuildingType } from '../building-type.model';

import { BuildingTypeService } from './building-type.service';

describe('BuildingType Service', () => {
  let service: BuildingTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IBuildingType;
  let expectedResult: IBuildingType | IBuildingType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BuildingTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      nameAr: 'AAAAAAA',
      nameEn: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BuildingType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BuildingType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BuildingType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          nameAr: 'BBBBBB',
          nameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BuildingType', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          nameAr: 'BBBBBB',
        },
        new BuildingType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BuildingType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          nameAr: 'BBBBBB',
          nameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BuildingType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBuildingTypeToCollectionIfMissing', () => {
      it('should add a BuildingType to an empty array', () => {
        const buildingType: IBuildingType = { id: 123 };
        expectedResult = service.addBuildingTypeToCollectionIfMissing([], buildingType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(buildingType);
      });

      it('should not add a BuildingType to an array that contains it', () => {
        const buildingType: IBuildingType = { id: 123 };
        const buildingTypeCollection: IBuildingType[] = [
          {
            ...buildingType,
          },
          { id: 456 },
        ];
        expectedResult = service.addBuildingTypeToCollectionIfMissing(buildingTypeCollection, buildingType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BuildingType to an array that doesn't contain it", () => {
        const buildingType: IBuildingType = { id: 123 };
        const buildingTypeCollection: IBuildingType[] = [{ id: 456 }];
        expectedResult = service.addBuildingTypeToCollectionIfMissing(buildingTypeCollection, buildingType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(buildingType);
      });

      it('should add only unique BuildingType to an array', () => {
        const buildingTypeArray: IBuildingType[] = [{ id: 123 }, { id: 456 }, { id: 35343 }];
        const buildingTypeCollection: IBuildingType[] = [{ id: 123 }];
        expectedResult = service.addBuildingTypeToCollectionIfMissing(buildingTypeCollection, ...buildingTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const buildingType: IBuildingType = { id: 123 };
        const buildingType2: IBuildingType = { id: 456 };
        expectedResult = service.addBuildingTypeToCollectionIfMissing([], buildingType, buildingType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(buildingType);
        expect(expectedResult).toContain(buildingType2);
      });

      it('should accept null and undefined values', () => {
        const buildingType: IBuildingType = { id: 123 };
        expectedResult = service.addBuildingTypeToCollectionIfMissing([], null, buildingType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(buildingType);
      });

      it('should return initial array if no BuildingType is added', () => {
        const buildingTypeCollection: IBuildingType[] = [{ id: 123 }];
        expectedResult = service.addBuildingTypeToCollectionIfMissing(buildingTypeCollection, undefined, null);
        expect(expectedResult).toEqual(buildingTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
