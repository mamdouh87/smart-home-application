import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHardwareItems, HardwareItems } from '../hardware-items.model';

import { HardwareItemsService } from './hardware-items.service';

describe('HardwareItems Service', () => {
  let service: HardwareItemsService;
  let httpMock: HttpTestingController;
  let elemDefault: IHardwareItems;
  let expectedResult: IHardwareItems | IHardwareItems[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HardwareItemsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      hardwareDescAr: 'AAAAAAA',
      hardwareDescEn: 'AAAAAAA',
      supportedQty: 0,
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

    it('should create a HardwareItems', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new HardwareItems()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HardwareItems', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hardwareDescAr: 'BBBBBB',
          hardwareDescEn: 'BBBBBB',
          supportedQty: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HardwareItems', () => {
      const patchObject = Object.assign(
        {
          hardwareDescAr: 'BBBBBB',
          supportedQty: 1,
        },
        new HardwareItems()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HardwareItems', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hardwareDescAr: 'BBBBBB',
          hardwareDescEn: 'BBBBBB',
          supportedQty: 1,
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

    it('should delete a HardwareItems', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHardwareItemsToCollectionIfMissing', () => {
      it('should add a HardwareItems to an empty array', () => {
        const hardwareItems: IHardwareItems = { id: 123 };
        expectedResult = service.addHardwareItemsToCollectionIfMissing([], hardwareItems);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hardwareItems);
      });

      it('should not add a HardwareItems to an array that contains it', () => {
        const hardwareItems: IHardwareItems = { id: 123 };
        const hardwareItemsCollection: IHardwareItems[] = [
          {
            ...hardwareItems,
          },
          { id: 456 },
        ];
        expectedResult = service.addHardwareItemsToCollectionIfMissing(hardwareItemsCollection, hardwareItems);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HardwareItems to an array that doesn't contain it", () => {
        const hardwareItems: IHardwareItems = { id: 123 };
        const hardwareItemsCollection: IHardwareItems[] = [{ id: 456 }];
        expectedResult = service.addHardwareItemsToCollectionIfMissing(hardwareItemsCollection, hardwareItems);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hardwareItems);
      });

      it('should add only unique HardwareItems to an array', () => {
        const hardwareItemsArray: IHardwareItems[] = [{ id: 123 }, { id: 456 }, { id: 68093 }];
        const hardwareItemsCollection: IHardwareItems[] = [{ id: 123 }];
        expectedResult = service.addHardwareItemsToCollectionIfMissing(hardwareItemsCollection, ...hardwareItemsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hardwareItems: IHardwareItems = { id: 123 };
        const hardwareItems2: IHardwareItems = { id: 456 };
        expectedResult = service.addHardwareItemsToCollectionIfMissing([], hardwareItems, hardwareItems2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hardwareItems);
        expect(expectedResult).toContain(hardwareItems2);
      });

      it('should accept null and undefined values', () => {
        const hardwareItems: IHardwareItems = { id: 123 };
        expectedResult = service.addHardwareItemsToCollectionIfMissing([], null, hardwareItems, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hardwareItems);
      });

      it('should return initial array if no HardwareItems is added', () => {
        const hardwareItemsCollection: IHardwareItems[] = [{ id: 123 }];
        expectedResult = service.addHardwareItemsToCollectionIfMissing(hardwareItemsCollection, undefined, null);
        expect(expectedResult).toEqual(hardwareItemsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
