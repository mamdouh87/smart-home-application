import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRequirementItem, RequirementItem } from '../requirement-item.model';

import { RequirementItemService } from './requirement-item.service';

describe('RequirementItem Service', () => {
  let service: RequirementItemService;
  let httpMock: HttpTestingController;
  let elemDefault: IRequirementItem;
  let expectedResult: IRequirementItem | IRequirementItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RequirementItemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      sysCode: 'AAAAAAA',
      code: 'AAAAAAA',
      descriptionAr: 'AAAAAAA',
      descriptionEn: 'AAAAAAA',
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

    it('should create a RequirementItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RequirementItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequirementItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sysCode: 'BBBBBB',
          code: 'BBBBBB',
          descriptionAr: 'BBBBBB',
          descriptionEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequirementItem', () => {
      const patchObject = Object.assign(
        {
          sysCode: 'BBBBBB',
          descriptionEn: 'BBBBBB',
        },
        new RequirementItem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequirementItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sysCode: 'BBBBBB',
          code: 'BBBBBB',
          descriptionAr: 'BBBBBB',
          descriptionEn: 'BBBBBB',
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

    it('should delete a RequirementItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRequirementItemToCollectionIfMissing', () => {
      it('should add a RequirementItem to an empty array', () => {
        const requirementItem: IRequirementItem = { id: 123 };
        expectedResult = service.addRequirementItemToCollectionIfMissing([], requirementItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requirementItem);
      });

      it('should not add a RequirementItem to an array that contains it', () => {
        const requirementItem: IRequirementItem = { id: 123 };
        const requirementItemCollection: IRequirementItem[] = [
          {
            ...requirementItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addRequirementItemToCollectionIfMissing(requirementItemCollection, requirementItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequirementItem to an array that doesn't contain it", () => {
        const requirementItem: IRequirementItem = { id: 123 };
        const requirementItemCollection: IRequirementItem[] = [{ id: 456 }];
        expectedResult = service.addRequirementItemToCollectionIfMissing(requirementItemCollection, requirementItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requirementItem);
      });

      it('should add only unique RequirementItem to an array', () => {
        const requirementItemArray: IRequirementItem[] = [{ id: 123 }, { id: 456 }, { id: 89343 }];
        const requirementItemCollection: IRequirementItem[] = [{ id: 123 }];
        expectedResult = service.addRequirementItemToCollectionIfMissing(requirementItemCollection, ...requirementItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requirementItem: IRequirementItem = { id: 123 };
        const requirementItem2: IRequirementItem = { id: 456 };
        expectedResult = service.addRequirementItemToCollectionIfMissing([], requirementItem, requirementItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requirementItem);
        expect(expectedResult).toContain(requirementItem2);
      });

      it('should accept null and undefined values', () => {
        const requirementItem: IRequirementItem = { id: 123 };
        expectedResult = service.addRequirementItemToCollectionIfMissing([], null, requirementItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requirementItem);
      });

      it('should return initial array if no RequirementItem is added', () => {
        const requirementItemCollection: IRequirementItem[] = [{ id: 123 }];
        expectedResult = service.addRequirementItemToCollectionIfMissing(requirementItemCollection, undefined, null);
        expect(expectedResult).toEqual(requirementItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
